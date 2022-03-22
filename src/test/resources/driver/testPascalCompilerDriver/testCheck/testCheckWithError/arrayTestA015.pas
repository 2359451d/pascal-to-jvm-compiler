{$mode iso}
{$RANGECHECKS ON}
program arrayTestA015;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;

  enum: weather;

begin
  enum := arrEnum[sunny];
  enum := arrEnum; {invalid, right operand is array of weather}
end.
