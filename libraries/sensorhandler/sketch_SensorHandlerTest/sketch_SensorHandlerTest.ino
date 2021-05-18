#include "SensorHandler.h"

SensorHandler arduinoSensors;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  // This loop is a crappy clear screen.
  for(int i = 0; i <= 40; i++){
      Serial.println(" ");
  }
  arduinoSensors.loop();
  Serial.print("Ultrasonic: ");
  Serial.println(arduinoSensors.getProximity());
  Serial.print("Line: ");
  switch(arduinoSensors.getBoundary()){
    case(SensorHandler::lineSensorState::LEFT_LINE_SENSOR_TRUE):
      Serial.println("LEFT_LINE_SENSOR_TRUE");
    break;
    case(SensorHandler::lineSensorState::RIGHT_LINE_SENSOR_TRUE):
      Serial.println("RIGHT_LINE_SENSOR_TRUE");
    break;
    case(SensorHandler::lineSensorState::BOTH_LINE_SENSOR_TRUE):
      Serial.println("BOTH_LINE_SENSOR_TRUE");
    break;
    case(SensorHandler::lineSensorState::BOTH_LINE_SENSOR_FALSE):
      Serial.println("BOTH_LINE_SENSOR_FALSE");
    break;
    default:
      Serial.println("ERROR");
    break;
  }
  delay(500);
}