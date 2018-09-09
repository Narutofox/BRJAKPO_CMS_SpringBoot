var tm_fonts = "Andale Mono=andale mono,times;" +
        "Arial=arial,helvetica,sans-serif;" +
        "Arial Black=arial black,avant garde;" +
        "Book Antiqua=book_antiquaregular,palatino;" +
        "Corda Light=CordaLight,sans-serif;" +
        "Courier New=courier_newregular,courier;" +
        "Flexo Caps=FlexoCapsDEMORegular;" +
        "Lucida Console=lucida_consoleregular,courier;" +
        "Georgia=georgia,palatino;" +
        "Helvetica=helvetica;" +
        "Impact=impactregular,chicago;" +
        "Museo Slab=MuseoSlab500Regular,sans-serif;" +
        "Museo Sans=MuseoSans500Regular,sans-serif;" +
        "Oblik Bold=OblikBoldRegular;" +
        "Sofia Pro Light=SofiaProLightRegular;" +
        "Symbol=webfontregular;" +
        "Tahoma=tahoma,arial,helvetica,sans-serif;" +
        "Terminal=terminal,monaco;" +
        "Tikal Sans Medium=TikalSansMediumMedium;" +
        "Times New Roman=times new roman,times;" +
        "Trebuchet MS=trebuchet ms,geneva;" +
        "Verdana=verdana,geneva;" +
        "Webdings=webdings;" +
        "Wingdings=wingdings,zapf dingbats" +
        "Aclonica=Aclonica, sans-serif;" +
        "Michroma=Michroma;" +
        "Paytone One=Paytone One, sans-serif;" +
        "Andalus=andalusregular, sans-serif;" +
        "Arabic Style=b_arabic_styleregular, sans-serif;" +
        "Andalus=andalusregular, sans-serif;" +
        "KACST_1=kacstoneregular, sans-serif;" +
        "Mothanna=mothannaregular, sans-serif;" +
        "Nastaliq=irannastaliqregular, sans-serif;" +
        "Samman=sammanregular;";

tinymce.init({
    selector: '.htmlContent',
    language: "hr",
    font_formats: tm_fonts,
    fontsize_formats: '8pt 10pt 12pt 14pt 18pt 24pt 36pt',
    menubar: 'insert file edit', height: "50em",
    plugins: ['link image textcolor colorpicker lists table anchor fullpage emoticons searchreplace'],
    toolbar1: 'undo redo | insert | styleselect | bold italic |  fontselect |  fontsizeselect | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | forecolor backcolor | table anchor fullpage emoticons searchreplace'
});
tinyMCE.activeEditor.dom.addStyle('p {margin:0; padding: 0;}');