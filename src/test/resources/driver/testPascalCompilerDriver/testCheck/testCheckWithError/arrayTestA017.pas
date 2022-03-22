{$mode iso}
{$RANGECHECKS ON}
program arrayTestA017;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;
  arrEnum2: array [weather] of (sunny1, cloudy1);

  enum: weather;

begin
  enum := arrEnum[sunny];
  enum := arrEnum2[sunny]; {Incompatible types: got (sunny1, cloudy1) expected "weather"}
end.
