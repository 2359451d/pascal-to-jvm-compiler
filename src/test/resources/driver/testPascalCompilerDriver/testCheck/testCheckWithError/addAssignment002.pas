program addAssignment002;
var
  str: packed array [1..6] of char;
begin
  str := 'string';
  str := 3+4; (* try to assign integer to a string varaible *)
end.
