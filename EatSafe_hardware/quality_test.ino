#include <ThingSpeak.h>
#include <DHTesp.h>
#include <dht.h>
#include <SoftwareSerial.h>
SoftwareSerial espSerial = SoftwareSerial(2,3);
#define DHTPIN 3     // what digital pin we're   connected to 
dht DHT;
#define DHTTYPE DHT11
#define led 7
#define airquality_sensor_pin 0
#define gas_sensor_pin 1
#define VAL_PROBE 2 // Analog pin 3
//DHT dht(DHTPIN,DHTTYPE);
String apiKey = "N1AUP12LQMYYVD0D"; // replace with your 

String channelId= "431218" ;
String ssid="123"; // Wifi network SSID
String password ="12345678"; // Wifi network password
boolean DEBUG=true;
void showResponse(int waitTime)
{
long t=millis();
char c;
while (t+waitTime>millis())
{
if (espSerial.available())
{
c=espSerial.read();
if (DEBUG)
Serial.print(c);
}
}
}
boolean thingSpeakWrite(float value1, float value2, int value3, int 
value4, int value5)
{
String cmd = "AT+CIPSTART=\"TCP\",\""; // TCP connection
cmd += "api.thingspeak.com";
cmd += "\",80";
espSerial.println(cmd);
if (DEBUG)
//Serial.println(cmd);
if(espSerial.find("Error"))
{
if (DEBUG)
//Serial.println("AT+CIPSTART error");
return false;
}
String getStr = "GET /update?api_key="; // prepare GET string
getStr += apiKey;
getStr +="&field1=";
getStr += String(value1);
getStr +="&field2=";
getStr += String(value2);
getStr +="&field3=";
getStr += String(value3);
getStr +="&field4=";
getStr += String(value4);
getStr +="&field5=";
getStr += String(value5);
// ...
getStr += "\r\n\r\n";
// send data length
cmd = "AT+CIPSEND=";
cmd += String(getStr.length());
espSerial.println(cmd);
if (DEBUG) Serial.println(cmd);
delay(100);
if(espSerial.find(">"))
{
espSerial.print(getStr);
if (DEBUG)
Serial.print(getStr);
}
else
{
//espSerial.println("AT+CIPCLOSE");
// alert user
if (DEBUG)
//Serial.println("AT+CIPCLOSE");
return false;
}
return true;
}
void setup(){
DEBUG=true; // enable debug serial
Serial.begin(9600);
espSerial.begin(115200);
espSerial.println("AT+RST");
showResponse(1000);
espSerial.println("AT+UART_CUR=9600,8,1,0,0");
showResponse(1000);
espSerial.println("AT+CWMODE=1"); // set esp8266 as client
showResponse(1000);
espSerial.println("AT+CWJAP=\""+ssid+"\",\""+password+"\""); // set your 
home router SSID and password
showResponse(5000);
if (DEBUG)
Serial.println("Setup completed");
}
void loop()
{
// Read sensor values

int err;
int airquality_value = analogRead(airquality_sensor_pin);
int gas_value = analogRead(gas_sensor_pin);
int moisture = analogRead(VAL_PROBE);
float chk =DHT.read11(DHTPIN);
float temp=DHT.temperature;
float humi=DHT.humidity;
Serial.println();
Serial.print("{\"temperature\":");
Serial.print(temp);
Serial.print(",\"humidity\":");
Serial.print(DHT.humidity, 1);
Serial.print(",\"Gas Value\":");
Serial.print(gas_value);

Serial.print(",\"Air Quality Value\":");
Serial.print(airquality_value);

Serial.print(",\"Soil Moisture Value\":");
Serial.print(1000-moisture);
Serial.print("}");

//Serial.println("% send to Thingspeak");
//Serial.print(temp);Serial.print(',');Serial.print(humi);
//Serial.print(',');
//Serial.print(gas_value);
//Serial.print(',');
//Serial.print(airquality_value);
//Serial.print(',');
//Serial.print(1000 - moisture);
//Serial.print("______________\n");
delay(10000);
//thingSpeakWrite(temp,humi,gas_value,airquality_value,moisture);
// Write values to thingspeak
// thingspeak needs 15 sec delay between updates,
delay(2000);
}

