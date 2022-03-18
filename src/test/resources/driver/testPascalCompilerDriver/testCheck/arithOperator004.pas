(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator003;
var
  a,b,c : integer;
  d: real;
  e,f: Integer;
begin
  {some statements below are not valid}

  f := 10 / 10; {Not work, since the result of real division is real}

  f := 10.0 div 10; {Not work since the operand cannot be real}

  f := 10.0 mod 10; {Not work, operands of modulus should be int}

  f := 10 + true;{Not work, operands must be real or int}
  f := 10 + 10.0;{Not work, right is real, but left is int}
  f := 10 * 10.0;{Not work, right is real, but left is int}

  f:= 10 * 10.0 - 10;{Not work, right is real, left is int}

  f := 10 + (10.0 + 1);{Not work, right is real, left is int}

  f:= -1.0;{Not work, left is int, right is real}
  f:= -true;{Not work, operand must be int or real}
end.