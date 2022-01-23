{$mode iso}
{$RANGECHECKS ON}
program arrayTest2;

type
  someType = array [1..10] of Integer;
  weather = (sunny, cloudy);
var
  res : Integer;
  card: (diamond, heart);

  arrVar1: array [1..10] of Integer;
  arrVar2: array [1..100] of Integer;


  charArrVar1: array ['a'..'f'] of Integer;
  charArrVar2: array ['a'..'a'] of Integer;

  boolArrVar1: array [false..true] of Integer;
  boolArrVar2: array [true..true] of Integer;

  enumArrVar1: array [weather] of Integer;
  enumArrVar2: array [sunny..cloudy] of Integer;
  enumArrVar3: array [sunny..sunny] of Integer;
  EnumArrVar4: array [diamond..heart] of Integer;

function sum(a:someType):Integer ;
var
  i:1..100 ;
  total: Integer;
begin
  total:=0;
  for i :=1 to 10 do
    total := total + a[i];
  a[1]:=100;
  sum:= total;
end;

begin
  enumArrVar1:=enumArrVar2;
  enumArrVar2:=enumArrVar1;
  enumArrVar1:=enumArrVar3;{incompatible array types}
  enumArrVar1:=EnumArrVar4;{incompatible array types}

  boolArrVar1:=boolArrVar2;{incompatible array types}
  charArrVar1:=charArrVar2;{incompatible array types}
  arrVar1:=arrVar2;{incompatible array types}

  arrVar1[1]:=100;

  {whole array operation, passed as acutal parameters}
  res:= sum(arrVar1);
  res:= sum(arrVar2); {Actual parameter cannot match the formal parameter}

end.