{$mode iso}
{$RANGECHECKS ON}
program arrayTestA019;

type
  weather = (sunny, cloudy);
weather2 = (sunny1, cloudy1);
var
  arrEnum2: array [weather] of weather2;

  enum: weather;
  enumVar2 : weather2;
begin
  enumVar2 := arrEnum2[sunny];
  enum := arrEnum2[sunny]; {Incompatible types: got (sunny1, cloudy1) expected "weather"}
end.
