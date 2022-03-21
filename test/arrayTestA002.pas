{$mode iso}
{$RANGECHECKS ON}
program arrayTestA;

type
  weather = (sunny, cloudy);
  subrangeType = 1..50;
  array1dim = array ['a'..'e'] of integer;
  arrType = array [1..50] of Integer;
var
  a: array1dim;
  i: char;
  n: integer;
  x: array [0..100] of array [1..50] of Integer;
  arrVar: arrType;

  arrVar2, arrVar2a: array [Boolean, 1..10] of real;
  arrVar3: array [Boolean] of array [1..10] of Integer;
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;

  arrEnum: array [weather] of weather;
  arrEnum2: array [weather] of (sunny1, cloudy1);

  enum: weather;
  card: (heart, cube);

  charArr : array [char] of Integer;

begin
  {char array, index range checking, any char...}
  charArr['1']:=1.0; {got "Single" expected "LongInt"}
end.