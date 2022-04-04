{$mode iso}
{$RANGECHECKS ON}
program arrayTestA017pre;

type
  weather = (sunny, cloudy);
var
  arrEnum: array [weather] of weather;
  arrEnum2: array [weather] of (sunny1, cloudy1);

  enum: weather;

begin
  enum := arrEnum[sunny];
end.
