{$mode iso}
{$RANGECHECKS ON}
program arrayTestB001;

type
  weather = (sunny, cloudy);
var
  enumArrVar1: array [weather] of Integer;
  enumArrVar2: array [sunny..cloudy] of Integer;
  enumArrVar3: array [sunny..sunny] of Integer;
begin
  enumArrVar1:=enumArrVar2;
  enumArrVar1:=enumArrVar3;{incompatible array types}
end.
