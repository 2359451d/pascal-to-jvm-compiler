(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program subrangeTest;
const
  flagT = true;
  flagF = false;
  flag = flagT;
  maxintConst = MaxInt;
  maxintConst2 = -maxint;
  intConst = 123;
  realConst = 12.75;
  charConst = 'A';
  strConst = 'ABC';
  {Enumeratives TBC}
var
  marks: 1 .. 100;
  grade: 'A' .. 'E';
  flagVar: Boolean;
  int1: Integer;
begin
  int1 := intConst;
  flagVar := flagT;
{  flag := false; {Not allowed, Variable identifier expected}

  {WriteLn(maxint);
  WriteLn(MaxInt);

  writeln( 'Enter your marks(1 - 100): ');
  readln(marks);

  writeln( 'Enter your grade(A - E): ');
  readln(grade);

  writeln('Marks: ' , marks, ' Grade: ', grade);}
end.