{$mode iso}
{$RANGECHECKS ON}
program arrayTestA013pre;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;
begin
  arrEnum[sunny] := cloudy;
end.
