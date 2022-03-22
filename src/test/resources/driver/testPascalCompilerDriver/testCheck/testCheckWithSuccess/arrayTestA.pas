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

  arrVar2, arrVar2_: array [Boolean, 1..10] of real;
  arrVar3: array [Boolean] of array [1..10] of Integer;
  arrVar4: array [1..9, 10..19] of array [Boolean, subrangeType] of Integer;

  arrEnum: array [weather] of weather;
  arrEnum2: array [weather] of (sunny1, cloudy1);

  enum: weather;
  card: (heart, cube);

  charArr : array [char] of Integer;

begin
  {char array, index range checking, any char...}
  charArr['A'] :=1;

  {array assignment}
  arrVar2 := arrVar2_;

  arrVar4[1, 12][true, 50] := 1;
  n := arrVar4[1, 12][true, 50];

  arrVar3[true][1] := 1;

  arrEnum[sunny] := cloudy;

  enum := arrEnum[sunny];

  x[0][1] := 100;
  x[0, 2] := 1000;

  n := 0;
  n := x[0][1];

  arrVar := x[0];


  (* loop to show you can use chars to index arrays *)
  for i := 'a' to 'e' do
  begin
    n := n + 5;
    a[i] := n;
  end;
end.
