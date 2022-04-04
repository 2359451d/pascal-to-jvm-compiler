{$mode iso}
{$RANGECHECKS ON}
program arrayTestA018;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;
  arrEnum2: array [weather] of (sunny1, cloudy1);

  enum: weather;
begin
  enum := arrEnum[sunny];
  enum := arrEnum2[sunny1]; {Incompatible types: got "<enumeration type>" expected "weather"}
end.
