(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/02/2022
 *)
program relationalOpTest(output);
var
  bool1: Boolean;
  int1: Integer;
  char1,char2:Char;
begin
  bool1 := 10 < 0;
  WriteLn(bool1);

  bool1 := (10 > 0) or (0 > 5);
  WriteLn(bool1);

  WriteLn((10 > 0) and (0 > 5));

  WriteLn('compare boolean');
  WriteLn(false<true);

  WriteLn(false>true);

  WriteLn('a<b=','a'<'b');
  WriteLn(1.0<=1);
  WriteLn('ab'='a');

  {char1:= 'a';
  char2:= 'b';
  bool1:= char1<char2;}
  {TODO}
  {bool1 := 'a'> 'b';}

  (*WriteLn(bool1); {f}
  WriteLn('a'>'b'); {f}
  WriteLn('abc'<'abd');{t}*)

  {bool1 := 1.0 > 1;}
{  bool1 := not 1;
  bool1 := 1 and true;
  bool1 := 1.0 or 1.0;
  int1 := true and false;}
{  bool1 := 10>0 and 0>5;}

end.