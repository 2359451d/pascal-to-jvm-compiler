program strAssignment002;
var
  str2: packed array[1..6] of char;
begin
  str2 := 'string';
  str2 := 'string1234'; {only with same length can be assigned to a string type}
end.
