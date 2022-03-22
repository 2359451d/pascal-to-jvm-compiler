program strAssignment001;
var
  str: array [1..6] of char;
  str2: packed array[1..6] of char;
begin
  str2 := 'string';
  str := 'string'; {only packed char array is string}
end.
