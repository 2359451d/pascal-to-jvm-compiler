{$mode iso}
{$RANGECHECKS ON}
program arrayTest2;

type
  someType = array [1..10] of Integer;
var
  res : Integer;

  arrVar1: array [1..10] of Integer;
  arrVar2: array [1..100] of Integer;

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
  {whole array operation, passed as acutal parameters}
  res:= sum(arrVar1);
  res:= sum(arrVar2); {Actual parameter cannot match the formal parameter}
end.