{$mode iso}
{$RANGECHECKS ON}
program arrayTestA015pre;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;

  enum: weather;

begin
  enum := arrEnum[sunny];
end.
