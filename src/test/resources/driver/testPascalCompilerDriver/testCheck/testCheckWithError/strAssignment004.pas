program strAssignment004;
var
  str2: packed array[1..6] of char;
  flag: Boolean;
begin
  str2 := 'string';
  flag:= str2 = 'string';
  flag:= 'string1'=str2; {relational op only applies on string with the same length}
end.
