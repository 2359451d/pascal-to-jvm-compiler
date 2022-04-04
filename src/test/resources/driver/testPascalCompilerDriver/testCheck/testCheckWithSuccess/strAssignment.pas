program strAssignment;
var
  str2: packed array[1..6] of char;
  flag: Boolean;
begin
  str2 := 'string';
  flag := str2 < 'string';
  flag := str2 = 'string';
  flag := str2 > 'string';
  flag := str2 <= 'string';
  flag := str2 >= 'string';
  flag := 'string' = str2;

  flag := 'string' > 'string';
  flag := 'string' = 'string';
  flag := 'string' < 'string';
  flag := 'string' >= 'string';
  flag := 'string' <= 'string';
end.
