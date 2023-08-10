// <https://stackoverflow.com/a/9252908>
function RemoveInlineStyles(El) {
    El.removeAttribute('style');
    if (El.childNodes.length > 0) {
        for (let Child in El.childNodes) {
            /* filter element nodes only */
            if (El.childNodes[Child].nodeType == 1)
                RemoveInlineStyles(El.childNodes[Child]);
        };
    };
};

function RemoveStyleSheets() {
    for (let El of document.getElementsByTagName('link')) {
        if (String(El.getAttribute('rel')).toLowerCase() == 'stylesheet' || String(El.getAttribute('type')).toLowerCase() == 'text/css')
            El.parentNode.removeChild(El);
    };
    for (let El of document.getElementsByTagName('style')) {
        El.parentNode.removeChild(El);
    };
};

function InhibitInlineStyles(El) {
    El.setAttribute('browseroctodisabledstyle', El.getAttribute('style'));
    El.removeAttribute('style');
    if (El.childNodes.length > 0) {
        for (let Child in El.childNodes) {
            /* filter element nodes only */
            if (El.childNodes[Child].nodeType == 1)
                InhibitInlineStyles(El.childNodes[Child]);
        };
    };
};

function InhibitStyleSheets() {
    for (let El of document.getElementsByTagName('link')) {
        if (String(El.getAttribute('rel')).toLowerCase() == 'stylesheet' || String(El.getAttribute('type')).toLowerCase() == 'text/css')
            ChangeElementTag(El);
    };
    for (let El of document.getElementsByTagName('style')) {
        ChangeElementTag(El);
    };
};

function ChangeElementTag(El) {
    let NewTag = `browseroctodisabled${El.tagName}`;
    El.outerHTML = `<${NewTag}${El.outerHTML.split(El.tagName).slice(1, -1).join(El.tagName)}${NewTag}>`;
};

//RemoveInlineStyles(document.body);
//RemoveStyleSheets();

InhibitInlineStyles(document.body);
InhibitStyleSheets();