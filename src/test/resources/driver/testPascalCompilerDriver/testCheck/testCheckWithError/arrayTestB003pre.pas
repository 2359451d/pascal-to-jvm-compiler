{$mode iso}
{$RANGECHECKS ON}
program arrayTestB003pre;

type
  weather = (sunny, cloudy);
var
  enumArrVar1: array [weather] of Integer;
  enumArrVar2: array [sunny..cloudy] of Integer;
  EnumArrVar4: array [diamond..heart] of Integer;

begin
  enumArrVar1:=enumArrVar2;
end.
