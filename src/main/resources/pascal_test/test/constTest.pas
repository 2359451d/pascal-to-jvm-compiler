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

  minIntConst =-2147483648 ; {Works}
  notWorkIntConst = 2147483648;{Overflows}
  notWorkIntConst2 = -2147483649;{undeflow}
  overflowConst =+2147483648 ;{overflows}


  {chrConst = chr(65);} {CHAR, Evaluated from chr() ,TBC}
  {Enumeratives TBC}
var
  flagVar: Boolean;
  int1: Integer;
  strVar: string;
  charVar: char;
begin
  charVar := strConst; {Illegal}
  strVar := strConst;
  strVar := charConst;


  int1 := intConst;
  flagVar := flagT;

  {[-2147483648] and [2147483647]}
  int1 := notWorkIntConst; {Illegal assignment [int1:=notWorkIntConst] with invalid constant right operand}
  int1 := +notWorkIntConst; {Illegal assignment [int1:=+notWorkIntConst]}
  int1 := -notWorkIntConst; {-2147483648 works, no overflow}

  int1 := -2147483648;
  int1 := 2147483648; {overflows}
  int1 := -2147483649; {underflows}

  flag := false; {Not allowed, Variable identifier expected, const reassignment}

  int1 := -(-notWorkIntConst); {--2147483648 overflows, right: expr}
  int1 := +(+notWorkIntConst); {overflows}

  int1 := +(+(-notWorkIntConst)); {-, works}
  int1 := -(+notWorkIntConst);{-+2147483648 works}

  int1 := -(-2147483648); {overflows}
  int1 := -(-(+2147483648)); {overflows}
  int1 := -(+(-2147483648)); {overflows}
  int1 := -(+2147483648); {works}


end.