(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/02/2022
 *)
program printProcTest;
procedure printX(x,y:Integer; ch: Char; flag:boolean; float1:real);
var
  intVar1,intVar2:Integer;
  boolVar:Boolean;
  charVar:Char;
  floatVar:Real;
begin
  WriteLn('boolvar',boolvar);
  WriteLn('float',floatVar);
WriteLn('charvar',charVar);

WriteLn(x);
WriteLn(x+1);
WriteLn(y);
WriteLn(intVar1);

end;
var
  intVarGlobal: Integer;

begin
  {printX(1);}
  {intVar1:= 5;
  WriteLn('hahhh');
  WriteLn(intVar1);
  WriteLn('hahhh',intVar1);
  WriteLn('hahhh',intVar1, -999);
  WriteLn(true);
  WriteLn();
  WriteLn;
  WriteLn(1*2 + (3+1));
Write(not true);}
  WriteLn(123);


  {printX(intVar1);}
end.