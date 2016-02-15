//  This program uses FastLED library, NOT Adafruit_NeoPixel
#include <FastLED.h>

#ifdef __AVR__
  #include <avr/power.h>
#endif

#include <Wire.h>

// Note: assumes NeoPixels are connected to PIN 6 on the Arduino!
#define NUM_LEDS 40
#define PIN 6

#define CHIPSET     NEOPIXEL

#define BRIGHTNESS  150
#define FRAMES_PER_SECOND 120

// Parameter 1 = number of pixels in strip
// Parameter 2 = Arduino pin number (most are valid)
// Parameter 3 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)

CRGB ledStrip[NUM_LEDS];

// states defined for the arduino.  These equate to strings received by the Roborio.
enum ColorState { inactive, autonomous, teleop, test };

#define INACTIVE 0
#define AUTO 1
#define TELEOP 2
#define TEST 3

// globals
ColorState cs = inactive;
CRGB stripColor;
CRGB teamColor;
uint8_t teamHue;

uint8_t fadeValue;
uint8_t brightness;
bool increasing;

// IMPORTANT: To reduce NeoPixel burnout risk, add 1000 uF capacitor across
// pixel power leads, add 300 - 500 Ohm resistor on first pixel's data input
// and minimize distance between Arduino and first pixel.  Avoid connecting
// on a live circuit...if you must, connect GND first.

/*********************** setup method **************************/
// called once during arduino startup, or on a board reset
void setup() {

  Serial.begin(57600);
  Serial.println("Starting up RioDuino!");

  Wire.begin(4);                // join i2c bus with address #4
  Wire.onReceive(receiveEvent); // register event

  // add main strip
  FastLED.addLeds<CHIPSET, PIN>(ledStrip, NUM_LEDS);
  FastLED.setBrightness( BRIGHTNESS );

  teamColor = CRGB::Blue;
  teamHue = 160;
  stripColor = CRGB::Green;

  fadeValue = 255;
  brightness = 0;
  increasing = false;
}

/************ loop method *************/
// running as fast as processor will allow
void loop() {

    // test patterns
    //colorWipe(teamColor,50);
    //colorWipe(CRGB::Green,50);
    //colorPulse(teamColor,1);
    //rainbow(30);
    //fireMethod();

    // change colors based on system state
    switch (cs) {
      case inactive:
        colorPulse(teamHue,5);
        break;
      case autonomous:
        colorWipe(stripColor,50);
        break;
      case teleop:
        colorWipe(stripColor,50);
        break;
      case test:
      default:
        rainbow(20);
        break;
    }
    
    // make sure we have a resonable delay between frames
    FastLED.delay(1000/FRAMES_PER_SECOND);
}

/***************** receiveEvent method ***********************/
// function that executes whenever data is received from i2c master
// this function is registered as an event, see setup()
void receiveEvent(int howMany)
{
  String receiveStr = "";

 // assemble the received chars into a string
  while ( Wire.available() > 0 )
  {
    char n=(char)Wire.read();
    if(((int)n)>((int)(' ')))
   receiveStr += n; 
  }

  Serial.println(receiveStr);

  // interpret the string to change the state of the arduino
  if (receiveStr == "colorBlue")
  {
    stripColor = CRGB::Blue;
  }
  else if (receiveStr == "colorRed")
  {
    stripColor = CRGB::Red;
  }
  else if (receiveStr == "colorPurple")
  {
    stripColor = CRGB::Purple;
  }
  else if (receiveStr == "colorYellow")
  {
    stripColor = CRGB::Yellow;
  }
  else if (receiveStr == "colorOrange")
  {
    stripColor = CRGB::Orange;
  }
  else if (receiveStr == "colorGreen")
  {
    stripColor = CRGB::Green;
  }
  else if (receiveStr == "colorGrey")
  {
    stripColor = CRGB::Grey;
  }
  else if (receiveStr == "colorBlack")
  {
    stripColor = CRGB::Black;
  }
  else if (receiveStr == "teamRed")
  {
    teamColor = CRGB::Red;
    teamHue = 0;
  }
  else if (receiveStr == "teamBlue")
  {
    teamColor = CRGB::Blue;
    teamHue = 160;
  }
  else if (receiveStr == "robotInit")
  {
    cs = inactive;    
  }
  else if (receiveStr == "autoInit")
  {
    cs = autonomous;
  }
  else if (receiveStr == "teleopInit")
  {
    cs = teleop;
  }
  else if (receiveStr == "testInit")
  {
    cs = test;
  }
  else if (receiveStr == "disabledInit")
  {
    cs = inactive;
  }
}

/*************** color pulse method ******************/
// pulses a color between on and off
// speed of the pulse is based on the wait parameter
void colorPulse(uint8_t teamHue, uint8_t wait)
{
    uint16_t i;
      
    // update fadeValue
    if (increasing)
    {
      fadeValue++;
    }
    else
    {
      fadeValue--;
    }

    // update direction based on limits
    if (fadeValue == 255)
    {
      increasing = false;
    }
    if (fadeValue == 40)
    {
        increasing = true;
    }

    for(i=0; i< NUM_LEDS; i++) {
      ledStrip[i] = CHSV(teamHue,255,fadeValue);
    }
    FastLED.show();
    delay(wait);
  
}

/*************** colorWipe method *********************/
// Fill the dots one after the other with a color
// speed of the fill is based on the wait parameter
void colorWipe(CRGB c, uint8_t wait) {
  for(uint16_t i=0; i<NUM_LEDS; i++) {
    ledStrip[i] = c;
  }
  FastLED.show();
  delay(wait);
}

/*************** rainbow method ********************/
// cycles through ALL colors for all neopixels 
// speed of cycle based on wait parameter
void rainbow(uint8_t wait) {
  uint16_t i;
  uint16_t hue;

  for(hue=0; hue<256; hue++) {
    for(i=0; i< NUM_LEDS; i++) {
      ledStrip[i] = CHSV(hue, 255, 255); 
    }
    FastLED.show();
    delay(wait);
  }
}

/******************** theaterChase method ****************/
//Theatre-style crawling lights.
void theaterChase(CRGB c, uint8_t wait) {
  for (int j=0; j<10; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      for (int i=0; i < NUM_LEDS; i=i+3) {
        ledStrip[i+q] = c;    //turn every third pixel on
      }

      for (int i=0; i < NUM_LEDS; i=i+3) {
        ledStrip[i+q] = CRGB::Black;        //turn every third pixel off
      }
      FastLED.show();
      
      delay(wait);
    }
  }
}

/****************************************************************************************/
/*********************************** Fire2012 code **************************************/
// Fire2012: a basic fire simulation for a one-dimensional string of LEDs
// Mark Kriegsman, July 2012.
//
// Compiled size for Arduino/AVR is about 3,968 bytes.


void fireMethod()
{
  // Add entropy to random number generator; we use a lot of it.
  random16_add_entropy( random());
  
  Fire2012(); // run simulation frame
  FastLED.show(); // display this frame
  
#if defined(FASTLED_VERSION) && (FASTLED_VERSION >= 2001000)
  FastLED.delay(1000 / FRAMES_PER_SECOND);
#else  
  delay(1000 / FRAMES_PER_SECOND);
#endif  ﻿
}


// Fire2012 by Mark Kriegsman, July 2012
// as part of "Five Elements" shown here: http://youtu.be/knWiGsmgycY
//
// This basic one-dimensional 'fire' simulation works roughly as follows:
// There's a underlying array of 'heat' cells, that model the temperature
// at each point along the line.  Every cycle through the simulation, 
// four steps are performed:
//  1) All cells cool down a little bit, losing heat to the air
//  2) The heat from each cell drifts 'up' and diffuses a little
//  3) Sometimes randomly new 'sparks' of heat are added at the bottom
//  4) The heat from each cell is rendered as a color into the leds array
//     The heat-to-color mapping uses a black-body radiation approximation.
//
// Temperature is in arbitrary units from 0 (cold black) to 255 (white hot).
//
// This simulation scales it self a bit depending on NUM_LEDS; it should look
// "OK" on anywhere from 20 to 100 LEDs without too much tweaking. 
//
// I recommend running this simulation at anywhere from 30-100 frames per second,
// meaning an interframe delay of about 10-35 milliseconds.
//
//
// There are two main parameters you can play with to control the look and
// feel of your fire: COOLING (used in step 1 above), and SPARKING (used
// in step 3 above).
//
// COOLING: How much does the air cool as it rises?
// Less cooling = taller flames.  More cooling = shorter flames.
// Default 55, suggested range 20-100 
#define COOLING  55

// SPARKING: What chance (out of 255) is there that a new spark will be lit?
// Higher chance = more roaring fire.  Lower chance = more flickery fire.
// Default 120, suggested range 50-200.
#define SPARKING 120


void Fire2012()
{
// Array of temperature readings at each simulation cell
  static byte heat[NUM_LEDS];

  // Step 1.  Cool down every cell a little
    for( int i = 0; i < NUM_LEDS; i++) {
      heat[i] = qsub8( heat[i],  random8(0, ((COOLING * 10) / NUM_LEDS) + 2));
    }
  
    // Step 2.  Heat from each cell drifts 'up' and diffuses a little
    for( int k= NUM_LEDS - 3; k > 0; k--) {
      heat[k] = (heat[k - 1] + heat[k - 2] + heat[k - 2] ) / 3;
    }
    
    // Step 3.  Randomly ignite new 'sparks' of heat near the bottom
    if( random8() < SPARKING ) {
      int y = random8(7);
      heat[y] = qadd8( heat[y], random8(160,255) );
    }

    // Step 4.  Map from heat cells to LED colors
    for( int j = 0; j < NUM_LEDS; j++) {
        ledStrip[j] = HeatColorUtil( heat[j]);
    }
}


// CRGB HeatColor( uint8_t temperature)
// [to be included in the forthcoming FastLED v2.1]
//
// Approximates a 'black body radiation' spectrum for 
// a given 'heat' level.  This is useful for animations of 'fire'.
// Heat is specified as an arbitrary scale from 0 (cool) to 255 (hot).
// This is NOT a chromatically correct 'black body radiation' 
// spectrum, but it's surprisingly close, and it's extremely fast and small.
//
// On AVR/Arduino, this typically takes around 70 bytes of program memory, 
// versus 768 bytes for a full 256-entry RGB lookup table.

CRGB HeatColorUtil( uint8_t temperature)
{
  CRGB heatcolor;
  
  // Scale 'heat' down from 0-255 to 0-191,
  // which can then be easily divided into three
  // equal 'thirds' of 64 units each.
  uint8_t t192 = scale8_video( temperature, 192);

  // calculate a value that ramps up from
  // zero to 255 in each 'third' of the scale.
  uint8_t heatramp = t192 & 0x3F; // 0..63
  heatramp <<= 2; // scale up to 0..252
 
  // now figure out which third of the spectrum we're in:
  if( t192 & 0x80) {
    // we're in the hottest third
    heatcolor.r = 255; // full red
    heatcolor.g = 255; // full green
    heatcolor.b = heatramp; // ramp up blue
    
  } else if( t192 & 0x40 ) {
    // we're in the middle third
    heatcolor.r = 255; // full red
    heatcolor.g = heatramp; // ramp up green
    heatcolor.b = 0; // no blue
    
  } else {
    // we're in the coolest third
    heatcolor.r = heatramp; // ramp up red
    heatcolor.g = 0; // no green
    heatcolor.b = 0; // no blue
  }
  
  return heatcolor;
}


