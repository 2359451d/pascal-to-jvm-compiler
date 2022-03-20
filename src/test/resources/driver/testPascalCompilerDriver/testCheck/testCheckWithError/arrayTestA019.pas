{$mode iso}
{$RANGECHECKS ON}
program arrayTestA;

type
  weather = (sunny, cloudy);
weather2 = (sunny1, cloudy1);
  subrangeType = 1..50;
  array1dim = array ['a'..'e'] of integer;
  arrType = array [1..50] of Integer;
var
  a: array1dim;
  i: char;
  n: integer;
  x: array [0..100] of array [1..50] of Integer;
  arrVar: arrType;

  arrVar2, arrVar2_: array [Boolean, 1..10] of real;
  arrVar3, arrVar3_: array [Boolean] of array [1..10] of Integer;
  subarrVar3: array [1..10] of Integer;
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;

  arrEnum: array [weather] of weather;
  arrEnum2: array [weather] of weather2;

  enum: weather;
  enumVar2 : weather2;
  card: (heart, cube);

  charArr : array [char] of Integer;

begin
  enumVar2 := arrEnum2[sunny];
  enum := arrEnum2[sunny]; {Incompatible types: got (sunny1, cloudy1) expected "weather"}
end.