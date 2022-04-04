{$mode iso}
{$RANGECHECKS ON}
program arrayTestA014;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;
begin
  arrEnum[sunny] := cloudy;
  arrEnum[sunny] := heart; {Illegal enumerated type assignment}
end.
