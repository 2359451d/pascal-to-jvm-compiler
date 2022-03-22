program addAssignment001;
var
  first, second, sum: integer;
begin
  first := 1;
  second :=2;
  sum := first+second;
  sum := first+second+0.0; {real assigned to int}
end.
