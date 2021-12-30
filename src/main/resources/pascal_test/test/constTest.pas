(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 29/12/2021
 *)
program constTest;
const
  {3 builtin constant, used without prior definition}
  flagT = true;
  flagF = false;
  flag = flagT;
  maxintConst = MaxInt;
  maxintConst2 = -maxint;

  intConst = 123; {int}
  intSignedConst = -123; {singed int}
  realConst = 12.75; {real}

  charConst = 'A'; {Remark, this would be recognized as StringLiteral}
  strConst = 'ABC';

  notWorkIntConst = 2147483648;


  {chrConst = chr(65);} {CHAR, Evaluated from chr() ,TBC}
  {Enumeratives TBC}
var
  flagVar: Boolean;
  int1: Integer;
begin
  int1 := intConst;
  flagVar := flagT;
  int1 := 2147483648;{overflows}
  int1 := -2147483649; {underflows}
{  flag := false; {Not allowed, Variable identifier expected}
end.