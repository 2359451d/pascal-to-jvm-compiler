{$mode iso}
{$RANGECHECKS ON}
program arrayTestB004pre;

type
  sub = false..true;
var
  boolArrVar1: array [false..true] of Integer;
  boolArrVar2: array [true..true] of Integer;
  boolArrVar3: array [sub] of Integer;

begin
  boolArrVar1:=boolArrVar3;
end.
