{$mode iso}
{$RANGECHECKS ON}
program arrayTestB001pre;

type
  weather = (sunny, cloudy);
var
  enumArrVar1: array [weather] of Integer;
  enumArrVar2: array [sunny..cloudy] of Integer;
  enumArrVar3: array [sunny..sunny] of Integer;
begin
  enumArrVar1:=enumArrVar2;
end.
