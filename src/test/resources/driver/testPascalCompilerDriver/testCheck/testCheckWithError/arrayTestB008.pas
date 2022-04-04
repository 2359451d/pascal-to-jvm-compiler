{$mode iso}
{$RANGECHECKS ON}
program arrayTestB008;

type
  someType = array [1..10] of Integer;
  subType = 1..199;
var
  res : Integer;
  subVar: 200..201;

  arrVar1: array [1..10] of Integer;
  arrVar2: array [1..100] of Integer;

function sum(a:someType; sub: subType):Integer ;
var
  i:1..100 ;
  total: Integer;
begin
  total:=0;
  for i :=1 to 10 do
    total := total + a[i];
  sum:= total
end;

begin
  subVar:=200;
  {whole array operation, passed as acutal parameters}
  res:= sum(arrVar2, subVar);
  res:= sum(arrVar1, subVar); {subrange is checked at the runtime}

end.
