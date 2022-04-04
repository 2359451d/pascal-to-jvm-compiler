(*
 * Project: pascal_test
 * User: Lenovo
 * Date: 14/02/2022
 *)
program printProcTest(input, output);

var
  globalInt:Integer;

procedure printX(x,y:Integer; ch: Char ;flag:boolean; float1:real);
{procedure printX(x,y:Integer);}
var
  intVar1,intVar2:Integer;
  boolVar:Boolean;
  charVar:Char;
  floatVar:Real;
begin
  globalInt:=999;
  WriteLn('globalInt ',globalInt);

  float1 := 1;
  WriteLn('float1 ', float1);
  WriteLn(float1 + 1);
  floatVar := 1.0;
  WriteLn('floatVar ', floatVar);
  WriteLn('floatVar+1 ', floatVar+1);

WriteLn();
  WriteLn(charVar);
  WriteLn(ch);
  ch := 'a';
  WriteLn(ch);
  WriteLn(flag);
  flag := false;
  WriteLn(flag);

  intVar1 := 1;
  WriteLn(intVar1);
  intVar1 := MaxInt + 555;
  WriteLn(intVar1);

  floatVar := 10000;
  WriteLn(floatVar);

  {WriteLn('boolvar',boolvar);
  WriteLn('float',floatVar);
  WriteLn('charvar',charVar);

  WriteLn(x);
  WriteLn(x+1);
  WriteLn(y);
  WriteLn(intVar1);}

end;
var
  intVarGlobal: Integer;
procedure printY(x:Integer);
begin
  intVarGlobal:=999;
  WriteLn('printY - intVarGlobal ', intVarGlobal);
end;
begin
  printY(1);
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

  printX(intVarGlobal, intVarGlobal, 'A', true, 0.5);
end.