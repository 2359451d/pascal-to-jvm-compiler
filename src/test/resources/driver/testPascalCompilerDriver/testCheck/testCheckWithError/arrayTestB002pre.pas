{$mode iso}
{$RANGECHECKS ON}
program arrayTestB002pre;

type
  weather = (sunny, cloudy);
var
  enumArrVar1: array [weather] of Integer;
  enumArrVar2: array [sunny..cloudy] of Integer;
  enumArrVar3: array [sunny..sunny] of Integer;
begin
  enumArrVar2:=enumArrVar1;
end.
