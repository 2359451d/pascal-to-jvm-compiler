{$mode iso}
{$RANGECHECKS ON}
program arrayTestA016;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;

  enum: weather;

begin
  enum := arrEnum[sunny];
  enum := arrEnum[heart]; {Invalid array scripting operation pos 0}
end.
