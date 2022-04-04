{$mode iso}
{$RANGECHECKS ON}
program arrayTest1;

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
  charArr['AS']:= 1; {got "Constant String" expected "Char"}
  charArr['1']:=1.0; {got "Single" expected "LongInt"}

  {array assignment}
  arrVar2 := arrVar2_;
  arrVar2 := arrVar3; {incompatible array type}

  arrVar4[1, 12][true, 50] := 1;
  n := arrVar4[1, 12][true, 50];
  arrVar4[1, 12][true, 51] := 1; {invalid index 3, range should be 10..19}
  n := arrVar4[1][2][true][50]; {invalid index 1, range should be 10..19}

  arrVar3[true][1] := 1;
  arrVar3[1][1] := 1; {invalid index pos 0, should be boolean}
  arrVar3[false][11] := 1; {invalid index pos 1, should be of subrange 1..10}
  arrVar3[true][1] := 1.0; {invalid, expected integer}
  arrVar3[true] := 1.0; {invalid, expected array of [1..10]}
  arrVar3 := 1.0; {invalid, expected array of [boolean, 1..10]}

  arrEnum[sunny] := cloudy;
  arrEnum[heart] := heart; {invalid array scripting pos 0}
  arrEnum[sunny] := heart; {Illegal enumerated type assignment}

  enum := arrEnum[sunny];
  enum := arrEnum; {invalid, right operand is array of weather}
  enum := arrEnum[heart]; {Invalid array scripting operation pos 0}
  enum := arrEnum2[sunny1]; {Incompatible types: got "<enumeration type>" expected "weather"}
  enum := arrEnum2[sunny]; {Incompatible types: got "<enumeration type>" expected "weather",arrEnum2: (sunny1, cloudy1)}

  x[0][1] := 100;
  x[0, 2] := 1000;
  n := x[0][1];

  x[101] := 100; {invalid scripting, pos 0 should be range of 0..100}
  x[100][0] := 100;  {invalid scripting, pos 1 should be range of 1..50}


  arrVar := x[0];
{  WriteLn(arrVar); } {cannot write directly, except for the packed char[] i.e. strings}

  n := 0;

  (* loop to show you can use chars to index arrays *)
  for i := 'a' to 'f' do
  begin
    n := n + 5;

    a[i] := n;
  end;
end.