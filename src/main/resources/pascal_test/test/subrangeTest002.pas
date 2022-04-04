program exSubrange;
var
  marks: 1 .. 100;
  grade: 'A' .. 'E';
begin
  writeln( 'Enter your marks(1 - 100): ');
  readln(marks);
  writeln( 'Enter your grade(A - E): ');
  readln(grade);
  writeln('Marks: ' , marks, ' Grade: ', grade);
end.