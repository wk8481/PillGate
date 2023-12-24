/*
 Setup your scale and start the sketch WITHOUT a weight on the scale
 Once readings are displayed place the weight on the scale
 Arduino pin 6 -> HX711 CLK
 Arduino pin 5 -> HX711 DOUT
 Arduino pin 5V -> HX711 VCC
 Arduino pin GND -> HX711 GND 
*/

#include "HX711.h"
#include <Arduino.h>


HX711 scale(5, 6);

float calibration_factor = 730; // this calibration factor is adjusted according to the load cell we are using
float units;
float ounces;

void setup() {
  Serial.begin(9600);

  scale.set_scale();
  scale.tare();  //Reset the scale to 0

  // to give time to user to get ready to put the box on the sensor  
  delay(3000);

}

void loop() {

  scale.set_scale(calibration_factor); //Adjust to this calibration factor

  Serial.print("Reading: ");
  units = scale.get_units(), 10;
  if (units < 0)
  {
    units = 0.00;
  }
  ounces = units * 0.035274;
  Serial.print(units);
  Serial.print(" grams"); 
  Serial.print(" calibration_factor: ");
  Serial.print(calibration_factor);
  Serial.println();

    // to give some time to the user to take the pill or box
    delay(3000);
}
