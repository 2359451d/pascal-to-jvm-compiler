program logicalOperator;
var
  a, b: boolean;

begin
  a := true;
  b := false;

  if (a and b) then
    writeln('Line 1 - Condition is true' )
  else
    writeln('Line 1 - Condition is not true');
  if  (a or b) then
    writeln('Line 2 - Condition is true' );

  (* lets change the value of  a and b *)
  a := false;
  b := true;

  if  (a and b) then
    writeln('Line 3 - Condition is true' )
  else
    writeln('Line 3 - Condition is not true' );

  if not (a and b) then
    writeln('Line 4 - Condition is true' );
end.
