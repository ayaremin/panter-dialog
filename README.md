# PanterDialog

[![Build Status](https://travis-ci.org/kngfrhzs/panter-dialog.svg?branch=master)](https://travis-ci.org/kngfrhzs/panter-dialog)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Panter%20Dialog-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4678) 
[![Join the chat at https://gitter.im/panterdialog/Lobby](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/panterdialog/Lobby)

![Logo](http://i.imgur.com/FMlRH4i.png)

![Screenshots](http://i.imgur.com/EmpgPMB.png)

## Installation 
Add this into your build.gradle dependencies section.
```
compile 'com.eminayar.panterdialog:panter-dialog:0.0.1.1'
```

## Sample Usages

You can check sample application to see how to create PanterDialogs with simply [sample module] (https://github.com/kngfrhzs/panter-dialog/tree/master/app).

There are 2 types of dialog for now. Default dialog type is `DialogType.Standart`. So if you don't pass this value it will create a standart text dialog. Otherwise you can create `Input Dialog` by just calling `setDialogType(DialogType.INPUT)`.

#### Dialog with Header and Logo
````java
new PanterDialog(this)
       .setHeaderBackground(R.drawable.pattern_bg_orange)
       .setHeaderLogo(R.drawable.sample_logo)
       .setPositive("I GOT IT")// You can pass also View.OnClickListener as second param
       .setNegative("DISMISS")
       .setMessage(R.string.lorem_ipsum)
       .isCancelable(false)
       .show();

````

#### Dialog with Header and Title
````java
new PanterDialog(this)
       .setHeaderBackground(R.drawable.pattern_bg_orange)
       .setTitle("Sample Title")
       .setPositive("I GOT IT")// You can pass also View.OnClickListener as second param
       .setNegative("DISMISS")
       .setMessage(R.string.lorem_ipsum)
       .isCancelable(false)
       .show();

````

#### Dialog with Only Header
````java
new PanterDialog(this)
       .setHeaderBackground(R.drawable.pattern_bg_orange)
       .setPositive("I GOT IT")// You can pass also View.OnClickListener as second param
       .setNegative("DISMISS")
       .setMessage(R.string.lorem_ipsum)
       .isCancelable(false)
       .show();

````

#### Input Dialog
````java
new PanterDialog(this)
        .setHeaderBackground(R.drawable.pattern_bg_orange)
        .setHeaderLogo(R.drawable.sample_logo)
        .setDialogType(DialogType.INPUT)
        .isCancelable(false)
        .input("THIS IS HINT FOR INPUT AREA YOU CAN WRITE HERE ANY LONGER TEXTS",
                "ERROR MESSAGE IF USER PUT EMPTY INPUT", new
                        PanterDialog
                                .OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                            }
                        })
        .show();
                                    

````

## Contribution

Open to any kind of new idea, development suggestion or bug fixing. And If anyone want to contribute , I will appreciate. It is enough to just create a new PR that explaining problem and solution.

###License
```
Copyright 2016 Muhammed Emin AYAR

The MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
