#include <SoftwareSerial.h>
#include "Driver.h";
#include "Planner.h";
#include "StateMachineHandler.h";
#include "SensorHandler.h";
#include "usbcommunicator.h";

Usbcommunicator arduinoUSB(Serial);
SensorHandler sensorHandler;
Driver driver;
Planner planner(&driver, &senorHandler);
StateMachineHandler stateMachine(arduinoUSB, senorHandler, driver, planner);

void setup() {
  // put your setup code here, to run once:
  arduinoUSB.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:
  driver.loop();
  sensorHandler.loop();
  stateMachine.loop();
  planner.loop();
}
