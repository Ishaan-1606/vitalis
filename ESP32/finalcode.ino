#include <WiFi.h>
#include <WebServer.h>
#include <PulseSensorPlayground.h>

const char* ssid = "*****";         
const char* password = "*****";  

const int PulseWire = 34;
int Threshold = 550;       
PulseSensorPlayground pulseSensor;

WebServer server(80);

// Array of dummy SPO2 values
int dummySPO2[] = {98, 98, 99, 97, 96, 99, 98, 96, 97, 95, 97};
int spo2Index = 0; // Changed 'index' to 'spo2Index'
int mySpo2 = 98;   // Global declaration to access it inside the loop

void handleRoot() {
  int myBPM = pulseSensor.getBeatsPerMinute();
  mySpo2 = dummySPO2[spo2Index];  // Get the current SPO2 value from the array

  if (myBPM != 0) {
    // Create JSON response with BPM and dummy SPO2
    String jsonResponse = "{\"bpm\": " + String(myBPM) + ", \"spo2\": " + String(mySpo2) + "}";
    spo2Index = (spo2Index + 1) % 11;  // Cycle through the dummy SPO2 values
    server.send(200, "application/json", jsonResponse);
  } else {
    server.send(500, "text/plain", "Error reading sensor.");
  }
}

void setup() {
  Serial.begin(115200); // Changed baud rate for consistency

  // Initialize PulseSensor
  pulseSensor.analogInput(PulseWire);  
  pulseSensor.setThreshold(Threshold);

  if (pulseSensor.begin()) {
    Serial.println("Pulse sensor initialized!");
  }

  // Connect to WiFi
  WiFi.begin(ssid, password);
  Serial.println("Connecting to WiFi...");
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting...");
  }

  Serial.println("Connected to WiFi");

  // Print IP Address
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());
  
  // Start server and define route
  server.on("/data", handleRoot);  // Ensure the route is "/data" to match your client-side requests
  server.begin();
  Serial.println("HTTP server started");
}

void loop() {
  server.handleClient();  // Handle incoming HTTP requests

  if (pulseSensor.sawStartOfBeat()) {
    int myBPM = pulseSensor.getBeatsPerMinute();
    // Serial.println("♥  A HeartBeat Happened!");
    // Serial.print("BPM: ");
    // Serial.println(myBPM);
    // Serial.print("SPO2: ");
    // Serial.println(mySpo2);  // This will print the current SPO2 value
  }

  delay(2000);
}
