(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 2021/12/4
 *)
program arithOperator;
var
  a,b,c : integer;
  d: real;
  e,f: Integer;
begin
  {statements below are valid}
  a := 21;
  b := 10;
  e := 25;
  c := a + b;
  c := a - b;
  c := a * b;
  d := a / b; {d is real, this works}
  d := a div b; {d is real, works though right output is integer}
  c := a mod b;
  c := a div b; {c is int}
  f := e div b; { 25 div 10}

  {some statements below are not valid}

  f := 10 / 10; {Not work, since the result of real division is real}
  d := 10/10; {works, d is real}

  f := 10 div 10; {works}
  d := 10 div 10; {works, though d is real, and output is int}
  f := 10.0 div 10; {Not work since the operand cannot be real}

  f := 10 mod 10; {works}
  d := 10 mod 10; {works though d is real, output is int}
  f := 10.0 mod 10; {Not work, operands of modulus should be int}

  f := 10 + true;{Not work, operands must be real or int}
  f := 10 + 10.0;{Not work, right is real, but left is int}
  f := 10 * 10.0;{Not work, right is real, but left is int}
end.