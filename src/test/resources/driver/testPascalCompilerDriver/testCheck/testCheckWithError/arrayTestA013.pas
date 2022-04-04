{$mode iso}
{$RANGECHECKS ON}
program arrayTestA013;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;
begin
  arrEnum[sunny] := cloudy;
  arrEnum[heart] := heart; {invalid array scripting pos 0}
end.
