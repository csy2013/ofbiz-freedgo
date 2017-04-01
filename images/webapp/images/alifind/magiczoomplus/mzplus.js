window.MagicZoom = (function () {
  var y, A;
  y = A = (function () {
    var U = {
      version: "v3.3-b4", UUID: 0, storage: {}, $uuid: function (Y) {
        return (Y.$J_UUID || (Y.$J_UUID = ++O.UUID))
      }, getStorage: function (Y) {
        return (O.storage[Y] || (O.storage[Y] = {}))
      }, $F: function () {
      }, $false: function () {
        return false
      }, $true: function () {
        return true
      }, stylesId: "mjs-" + Math.floor(Math.random() * new Date().getTime()), defined: function (Y) {
        return (undefined != Y)
      }, ifndef: function (Z, Y) {
        return (undefined != Z) ? Z : Y
      }, exists: function (Y) {
        return !!(Y)
      }, jTypeOf: function (Y) {
        if (!O.defined(Y)) {
          return false
        }
        if (Y.$J_TYPE) {
          return Y.$J_TYPE
        }
        if (!!Y.nodeType) {
          if (1 == Y.nodeType) {
            return "element"
          }
          if (3 == Y.nodeType) {
            return "textnode"
          }
        }
        if (Y.length && Y.item) {
          return "collection"
        }
        if (Y.length && Y.callee) {
          return "arguments"
        }
        if ((Y instanceof window.Object || Y instanceof window.Function) && Y.constructor === O.Class) {
          return "class"
        }
        if (Y instanceof window.Array) {
          return "array"
        }
        if (Y instanceof window.Function) {
          return "function"
        }
        if (Y instanceof window.String) {
          return "string"
        }
        if (O.browser.trident) {
          if (O.defined(Y.cancelBubble)) {
            return "event"
          }
        } else {
          if (Y === window.event || Y.constructor == window.Event || Y.constructor == window.MouseEvent || Y.constructor == window.UIEvent || Y.constructor == window.KeyboardEvent || Y.constructor == window.KeyEvent) {
            return "event"
          }
        }
        if (Y instanceof window.Date) {
          return "date"
        }
        if (Y instanceof window.RegExp) {
          return "regexp"
        }
        if (Y === window) {
          return "window"
        }
        if (Y === document) {
          return "document"
        }
        return typeof(Y)
      }, extend: function (ad, ac) {
        if (!(ad instanceof window.Array)) {
          ad = [ad]
        }
        if (!ac) {
          return ad[0]
        }
        for (var ab = 0, Z = ad.length; ab < Z; ab++) {
          if (!O.defined(ad)) {
            continue
          }
          for (var aa in ac) {
            if (!Object.prototype.hasOwnProperty.call(ac, aa)) {
              continue
            }
            try {
              ad[ab][aa] = ac[aa]
            } catch (Y) {
            }
          }
        }
        return ad[0]
      }, implement: function (ac, ab) {
        if (!(ac instanceof window.Array)) {
          ac = [ac]
        }
        for (var aa = 0, Y = ac.length; aa < Y; aa++) {
          if (!O.defined(ac[aa])) {
            continue
          }
          if (!ac[aa].prototype) {
            continue
          }
          for (var Z in(ab || {})) {
            if (!ac[aa].prototype[Z]) {
              ac[aa].prototype[Z] = ab[Z]
            }
          }
        }
        return ac[0]
      }, nativize: function (aa, Z) {
        if (!O.defined(aa)) {
          return aa
        }
        for (var Y in(Z || {})) {
          if (!aa[Y]) {
            aa[Y] = Z[Y]
          }
        }
        return aa
      }, $try: function () {
        for (var Z = 0, Y = arguments.length; Z < Y; Z++) {
          try {
            return arguments[Z]()
          } catch (aa) {
          }
        }
        return null
      }, $A: function (aa) {
        if (!O.defined(aa)) {
          return O.$([])
        }
        if (aa.toArray) {
          return O.$(aa.toArray())
        }
        if (aa.item) {
          var Z = aa.length || 0, Y = new Array(Z);
          while (Z--) {
            Y[Z] = aa[Z]
          }
          return O.$(Y)
        }
        return O.$(Array.prototype.slice.call(aa))
      }, now: function () {
        return new Date().getTime()
      }, detach: function (ac) {
        var aa;
        switch (O.jTypeOf(ac)) {
          case"object":
            aa = {};
            for (var ab in ac) {
              aa[ab] = O.detach(ac[ab])
            }
            break;
          case"array":
            aa = [];
            for (var Z = 0, Y = ac.length; Z < Y; Z++) {
              aa[Z] = O.detach(ac[Z])
            }
            break;
          default:
            return ac
        }
        return O.$(aa)
      }, $: function (aa) {
        var Y = true;
        if (!O.defined(aa)) {
          return null
        }
        if (aa.$J_EXT) {
          return aa
        }
        switch (O.jTypeOf(aa)) {
          case"array":
            aa = O.nativize(aa, O.extend(O.Array, {$J_EXT: O.$F}));
            aa.jEach = aa.forEach;
            return aa;
            break;
          case"string":
            var Z = document.getElementById(aa);
            if (O.defined(Z)) {
              return O.$(Z)
            }
            return null;
            break;
          case"window":
          case"document":
            O.$uuid(aa);
            aa = O.extend(aa, O.Doc);
            break;
          case"element":
            O.$uuid(aa);
            aa = O.extend(aa, O.Element);
            break;
          case"event":
            aa = O.extend(aa, O.Event);
            break;
          case"textnode":
          case"function":
          case"array":
          case"date":
          default:
            Y = false;
            break
        }
        if (Y) {
          return O.extend(aa, {$J_EXT: O.$F})
        } else {
          return aa
        }
      }, $new: function (Y, aa, Z) {
        return O.$(O.doc.createElement(Y)).setProps(aa || {}).jSetCss(Z || {})
      }, addCSS: function (Z, ab, af) {
        var ac, aa, ad, ae = [], Y = -1;
        af || (af = O.stylesId);
        ac = O.$(af) || O.$new("style", {id: af, type: "text/css"}).jAppendTo((document.head || document.body), "top");
        aa = ac.sheet || ac.styleSheet;
        if ("string" != O.jTypeOf(ab)) {
          for (var ad in ab) {
            ae.push(ad + ":" + ab[ad])
          }
          ab = ae.join(";")
        }
        if (aa.insertRule) {
          Y = aa.insertRule(Z + " {" + ab + "}", aa.cssRules.length)
        } else {
          Y = aa.addRule(Z, ab)
        }
        return Y
      }, removeCSS: function (ab, Y) {
        var aa, Z;
        aa = O.$(ab);
        if ("element" !== O.jTypeOf(aa)) {
          return
        }
        Z = aa.sheet || aa.styleSheet;
        if (Z.deleteRule) {
          Z.deleteRule(Y)
        } else {
          if (Z.removeRule) {
            Z.removeRule(Y)
          }
        }
      }, generateUUID: function () {
        return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (aa) {
          var Z = Math.random() * 16 | 0, Y = aa == "x" ? Z : (Z & 3 | 8);
          return Y.toString(16)
        }).toUpperCase()
      }, getAbsoluteURL: (function () {
        var Y;
        return function (Z) {
          if (!Y) {
            Y = document.createElement("a")
          }
          Y.setAttribute("href", Z);
          return ("!!" + Y.href).replace("!!", "")
        }
      })(), getHashCode: function (aa) {
        var ab = 0, Y = aa.length;
        for (var Z = 0; Z < Y; ++Z) {
          ab = 31 * ab + aa.charCodeAt(Z);
          ab %= 4294967296
        }
        return ab
      }
    };
    var O = U;
    var P = U.$;
    if (!window.magicJS) {
      window.magicJS = U;
      window.$mjs = U.$
    }
    O.Array = {
      $J_TYPE: "array", indexOf: function (ab, ac) {
        var Y = this.length;
        for (var Z = this.length, aa = (ac < 0) ? Math.max(0, Z + ac) : ac || 0; aa < Z; aa++) {
          if (this[aa] === ab) {
            return aa
          }
        }
        return -1
      }, contains: function (Y, Z) {
        return this.indexOf(Y, Z) != -1
      }, forEach: function (Y, ab) {
        for (var aa = 0, Z = this.length; aa < Z; aa++) {
          if (aa in this) {
            Y.call(ab, this[aa], aa, this)
          }
        }
      }, filter: function (Y, ad) {
        var ac = [];
        for (var ab = 0, Z = this.length; ab < Z; ab++) {
          if (ab in this) {
            var aa = this[ab];
            if (Y.call(ad, this[ab], ab, this)) {
              ac.push(aa)
            }
          }
        }
        return ac
      }, map: function (Y, ac) {
        var ab = [];
        for (var aa = 0, Z = this.length; aa < Z; aa++) {
          if (aa in this) {
            ab[aa] = Y.call(ac, this[aa], aa, this)
          }
        }
        return ab
      }
    };
    O.implement(String, {
      $J_TYPE: "string", jTrim: function () {
        return this.replace(/^\s+|\s+$/g, "")
      }, eq: function (Y, Z) {
        return (Z || false) ? (this.toString() === Y.toString()) : (this.toLowerCase().toString() === Y.toLowerCase().toString())
      }, jCamelize: function () {
        return this.replace(/-\D/g, function (Y) {
          return Y.charAt(1).toUpperCase()
        })
      }, dashize: function () {
        return this.replace(/[A-Z]/g, function (Y) {
          return ("-" + Y.charAt(0).toLowerCase())
        })
      }, jToInt: function (Y) {
        return parseInt(this, Y || 10)
      }, toFloat: function () {
        return parseFloat(this)
      }, jToBool: function () {
        return !this.replace(/true/i, "").jTrim()
      }, has: function (Z, Y) {
        Y = Y || "";
        return (Y + this + Y).indexOf(Y + Z + Y) > -1
      }
    });
    U.implement(Function, {
      $J_TYPE: "function", jBind: function () {
        var Z = O.$A(arguments), Y = this, aa = Z.shift();
        return function () {
          return Y.apply(aa || null, Z.concat(O.$A(arguments)))
        }
      }, jBindAsEvent: function () {
        var Z = O.$A(arguments), Y = this, aa = Z.shift();
        return function (ab) {
          return Y.apply(aa || null, O.$([ab || (O.browser.ieMode ? window.event : null)]).concat(Z))
        }
      }, jDelay: function () {
        var Z = O.$A(arguments), Y = this, aa = Z.shift();
        return window.setTimeout(function () {
          return Y.apply(Y, Z)
        }, aa || 0)
      }, jDefer: function () {
        var Z = O.$A(arguments), Y = this;
        return function () {
          return Y.jDelay.apply(Y, Z)
        }
      }, interval: function () {
        var Z = O.$A(arguments), Y = this, aa = Z.shift();
        return window.setInterval(function () {
          return Y.apply(Y, Z)
        }, aa || 0)
      }
    });
    var V = {}, N = navigator.userAgent.toLowerCase(), M = N.match(/(webkit|gecko|trident|presto)\/(\d+\.?\d*)/i), R = N.match(/(edge|opr)\/(\d+\.?\d*)/i) || N.match(/(crios|chrome|safari|firefox|opera|opr)\/(\d+\.?\d*)/i), T = N.match(/version\/(\d+\.?\d*)/i), I = document.documentElement.style;

    function J(Z) {
      var Y = Z.charAt(0).toUpperCase() + Z.slice(1);
      return Z in I || ("Webkit" + Y) in I || ("Moz" + Y) in I || ("ms" + Y) in I || ("O" + Y) in I
    }

    O.browser = {
      features: {
        xpath: !!(document.evaluate),
        air: !!(window.runtime),
        query: !!(document.querySelector),
        fullScreen: !!(document.fullscreenEnabled || document.msFullscreenEnabled || document.exitFullscreen || document.cancelFullScreen || document.webkitexitFullscreen || document.webkitCancelFullScreen || document.mozCancelFullScreen || document.oCancelFullScreen || document.msCancelFullScreen),
        xhr2: !!(window.ProgressEvent) && !!(window.FormData) && (window.XMLHttpRequest && "withCredentials" in new XMLHttpRequest),
        transition: J("transition"),
        transform: J("transform"),
        perspective: J("perspective"),
        animation: J("animation"),
        requestAnimationFrame: false,
        multibackground: false,
        cssFilters: false,
        canvas: false,
        svg: (function () {
          return document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#Image", "1.1")
        })()
      },
      touchScreen: function () {
        return "ontouchstart" in window || (window.DocumentTouch && document instanceof DocumentTouch) || (navigator.maxTouchPoints > 0) || (navigator.msMaxTouchPoints > 0)
      }(),
      mobile: N.match(/(android|bb\d+|meego).+|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od|ad)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/) ? true : false,
      engine: (M && M[1]) ? M[1].toLowerCase() : (window.opera) ? "presto" : !!(window.ActiveXObject) ? "trident" : (undefined !== document.getBoxObjectFor || null != window.mozInnerScreenY) ? "gecko" : (null !== window.WebKitPoint || !navigator.taintEnabled) ? "webkit" : "unknown",
      version: (M && M[2]) ? parseFloat(M[2]) : 0,
      uaName: (R && R[1]) ? R[1].toLowerCase() : "",
      uaVersion: (R && R[2]) ? parseFloat(R[2]) : 0,
      cssPrefix: "",
      cssDomPrefix: "",
      domPrefix: "",
      ieMode: 0,
      platform: N.match(/ip(?:ad|od|hone)/) ? "ios" : (N.match(/(?:webos|android)/) || navigator.platform.match(/mac|win|linux/i) || ["other"])[0].toLowerCase(),
      backCompat: document.compatMode && "backcompat" == document.compatMode.toLowerCase(),
      scrollbarsWidth: 0,
      getDoc: function () {
        return (document.compatMode && "backcompat" == document.compatMode.toLowerCase()) ? document.body : document.documentElement
      },
      requestAnimationFrame: window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || undefined,
      cancelAnimationFrame: window.cancelAnimationFrame || window.mozCancelAnimationFrame || window.mozCancelAnimationFrame || window.oCancelAnimationFrame || window.msCancelAnimationFrame || window.webkitCancelRequestAnimationFrame || undefined,
      ready: false,
      onready: function () {
        if (O.browser.ready) {
          return
        }
        var ab, aa;
        O.browser.ready = true;
        O.body = O.$(document.body);
        O.win = O.$(window);
        try {
          var Z = O.$new("div").jSetCss({width: 100, height: 100, overflow: "scroll", position: "absolute", top: -9999}).jAppendTo(document.body);
          O.browser.scrollbarsWidth = Z.offsetWidth - Z.clientWidth;
          Z.jRemove()
        } catch (Y) {
        }
        try {
          ab = O.$new("div");
          aa = ab.style;
          aa.cssText = "background:url(https://),url(https://),red url(https://)";
          O.browser.features.multibackground = (/(url\s*\(.*?){3}/).test(aa.background);
          aa = null;
          ab = null
        } catch (Y) {
        }
        if (!O.browser.cssTransformProp) {
          O.browser.cssTransformProp = O.normalizeCSS("transform").dashize()
        }
        try {
          ab = O.$new("div");
          ab.style.cssText = O.normalizeCSS("filter").dashize() + ":blur(2px);";
          O.browser.features.cssFilters = !!ab.style.length && (!O.browser.ieMode || O.browser.ieMode > 9);
          ab = null
        } catch (Y) {
        }
        if (!O.browser.features.cssFilters) {
          O.$(document.documentElement).jAddClass("no-cssfilters-magic")
        }
        try {
          O.browser.features.canvas = (function () {
            var ac = O.$new("canvas");
            return !!(ac.getContext && ac.getContext("2d"))
          })()
        } catch (Y) {
        }
        if (undefined === window.TransitionEvent && undefined !== window.WebKitTransitionEvent) {
          V.transitionend = "webkitTransitionEnd"
        }
        O.Doc.jCallEvent.call(O.$(document), "domready")
      }
    };
    (function () {
      var ad = [], ac, ab, Z;

      function Y() {
        return !!(arguments.callee.caller)
      }

      switch (O.browser.engine) {
        case"trident":
          if (!O.browser.version) {
            O.browser.version = !!(window.XMLHttpRequest) ? 3 : 2
          }
          break;
        case"gecko":
          O.browser.version = (R && R[2]) ? parseFloat(R[2]) : 0;
          break
      }
      O.browser[O.browser.engine] = true;
      if (R && "crios" === R[1]) {
        O.browser.uaName = "chrome"
      }
      if (!!window.chrome) {
        O.browser.chrome = true
      }
      if (R && "opr" === R[1]) {
        O.browser.uaName = "opera";
        O.browser.opera = true
      }
      if ("safari" === O.browser.uaName && (T && T[1])) {
        O.browser.uaVersion = parseFloat(T[1])
      }
      if ("android" == O.browser.platform && O.browser.webkit && (T && T[1])) {
        O.browser.androidBrowser = true
      }
      ac = ({gecko: ["-moz-", "Moz", "moz"], webkit: ["-webkit-", "Webkit", "webkit"], trident: ["-ms-", "ms", "ms"], presto: ["-o-", "O", "o"]})[O.browser.engine] || ["", "", ""];
      O.browser.cssPrefix = ac[0];
      O.browser.cssDomPrefix = ac[1];
      O.browser.domPrefix = ac[2];
      O.browser.ieMode = (!O.browser.trident) ? undefined : (document.documentMode) ? document.documentMode : function () {
        var ae = 0;
        if (O.browser.backCompat) {
          return 5
        }
        switch (O.browser.version) {
          case 2:
            ae = 6;
            break;
          case 3:
            ae = 7;
            break
        }
        return ae
      }();
      ad.push(O.browser.platform + "-magic");
      if (O.browser.mobile) {
        ad.push("mobile-magic")
      }
      if (O.browser.androidBrowser) {
        ad.push("android-browser-magic")
      }
      if (O.browser.ieMode) {
        O.browser.uaName = "ie";
        O.browser.uaVersion = O.browser.ieMode;
        ad.push("ie" + O.browser.ieMode + "-magic");
        for (ab = 11; ab > O.browser.ieMode; ab--) {
          ad.push("lt-ie" + ab + "-magic")
        }
      }
      if (O.browser.webkit && O.browser.version < 536) {
        O.browser.features.fullScreen = false
      }
      if (O.browser.requestAnimationFrame) {
        O.browser.requestAnimationFrame.call(window, function () {
          O.browser.features.requestAnimationFrame = true
        })
      }
      if (O.browser.features.svg) {
        ad.push("svg-magic")
      } else {
        ad.push("no-svg-magic")
      }
      Z = (document.documentElement.className || "").match(/\S+/g) || [];
      document.documentElement.className = O.$(Z).concat(ad).join(" ");
      try {
        document.documentElement.setAttribute("data-magic-ua", O.browser.uaName);
        document.documentElement.setAttribute("data-magic-ua-ver", O.browser.uaVersion)
      } catch (aa) {
      }
      if (O.browser.ieMode && O.browser.ieMode < 9) {
        document.createElement("figure");
        document.createElement("figcaption")
      }
    })();
    (function () {
      O.browser.fullScreen = {
        capable: O.browser.features.fullScreen,
        enabled: function () {
          return !!(document.fullscreenElement || document[O.browser.domPrefix + "FullscreenElement"] || document.fullScreen || document.webkitIsFullScreen || document[O.browser.domPrefix + "FullScreen"])
        },
        request: function (Y, Z) {
          Z || (Z = {});
          if (this.capable) {
            O.$(document).jAddEvent(this.changeEventName, this.onchange = function (aa) {
              if (this.enabled()) {
                Z.onEnter && Z.onEnter()
              } else {
                O.$(document).jRemoveEvent(this.changeEventName, this.onchange);
                Z.onExit && Z.onExit()
              }
            }.jBindAsEvent(this));
            O.$(document).jAddEvent(this.errorEventName, this.onerror = function (aa) {
              Z.fallback && Z.fallback();
              O.$(document).jRemoveEvent(this.errorEventName, this.onerror)
            }.jBindAsEvent(this));
            (Y[O.browser.domPrefix + "RequestFullscreen"] || Y[O.browser.domPrefix + "RequestFullScreen"] || Y.requestFullscreen || function () {
            }).call(Y)
          } else {
            if (Z.fallback) {
              Z.fallback()
            }
          }
        },
        cancel: (document.exitFullscreen || document.cancelFullScreen || document[O.browser.domPrefix + "ExitFullscreen"] || document[O.browser.domPrefix + "CancelFullScreen"] || function () {
        }).jBind(document),
        changeEventName: document.msExitFullscreen ? "MSFullscreenChange" : (document.exitFullscreen ? "" : O.browser.domPrefix) + "fullscreenchange",
        errorEventName: document.msExitFullscreen ? "MSFullscreenError" : (document.exitFullscreen ? "" : O.browser.domPrefix) + "fullscreenerror",
        prefix: O.browser.domPrefix,
        activeElement: null
      }
    })();
    var X = /\S+/g, L = /^(border(Top|Bottom|Left|Right)Width)|((padding|margin)(Top|Bottom|Left|Right))$/, Q = {"float": ("undefined" === typeof(I.styleFloat)) ? "cssFloat" : "styleFloat"}, S = {
      fontWeight: true,
      lineHeight: true,
      opacity: true,
      zIndex: true,
      zoom: true
    }, K = (window.getComputedStyle) ? function (aa, Y) {
      var Z = window.getComputedStyle(aa, null);
      return Z ? Z.getPropertyValue(Y) || Z[Y] : null
    } : function (ab, Z) {
      var aa = ab.currentStyle, Y = null;
      Y = aa ? aa[Z] : null;
      if (null == Y && ab.style && ab.style[Z]) {
        Y = ab.style[Z]
      }
      return Y
    };

    function W(aa) {
      var Y, Z;
      Z = (O.browser.webkit && "filter" == aa) ? false : (aa in I);
      if (!Z) {
        Y = O.browser.cssDomPrefix + aa.charAt(0).toUpperCase() + aa.slice(1);
        if (Y in I) {
          return Y
        }
      }
      return aa
    }

    O.normalizeCSS = W;
    O.Element = {
      jHasClass: function (Y) {
        return !(Y || "").has(" ") && (this.className || "").has(Y, " ")
      }, jAddClass: function (ac) {
        var Z = (this.className || "").match(X) || [], ab = (ac || "").match(X) || [], Y = ab.length, aa = 0;
        for (; aa < Y; aa++) {
          if (!O.$(Z).contains(ab[aa])) {
            Z.push(ab[aa])
          }
        }
        this.className = Z.join(" ");
        return this
      }, jRemoveClass: function (ad) {
        var Z = (this.className || "").match(X) || [], ac = (ad || "").match(X) || [], Y = ac.length, ab = 0, aa;
        for (; ab < Y; ab++) {
          if ((aa = O.$(Z).indexOf(ac[ab])) > -1) {
            Z.splice(aa, 1)
          }
        }
        this.className = ad ? Z.join(" ") : "";
        return this
      }, jToggleClass: function (Y) {
        return this.jHasClass(Y) ? this.jRemoveClass(Y) : this.jAddClass(Y)
      }, jGetCss: function (Z) {
        var aa = Z.jCamelize(), Y = null;
        Z = Q[aa] || (Q[aa] = W(aa));
        Y = K(this, Z);
        if ("auto" === Y) {
          Y = null
        }
        if (null !== Y) {
          if ("opacity" == Z) {
            return O.defined(Y) ? parseFloat(Y) : 1
          }
          if (L.test(Z)) {
            Y = parseInt(Y, 10) ? Y : "0px"
          }
        }
        return Y
      }, jSetCssProp: function (Z, Y) {
        var ab = Z.jCamelize();
        try {
          if ("opacity" == Z) {
            this.jSetOpacity(Y);
            return this
          }
          Z = Q[ab] || (Q[ab] = W(ab));
          this.style[Z] = Y + (("number" == O.jTypeOf(Y) && !S[ab]) ? "px" : "")
        } catch (aa) {
        }
        return this
      }, jSetCss: function (Z) {
        for (var Y in Z) {
          this.jSetCssProp(Y, Z[Y])
        }
        return this
      }, jGetStyles: function () {
        var Y = {};
        O.$A(arguments).jEach(function (Z) {
          Y[Z] = this.jGetCss(Z)
        }, this);
        return Y
      }, jSetOpacity: function (aa, Y) {
        var Z;
        Y = Y || false;
        this.style.opacity = aa;
        aa = parseInt(parseFloat(aa) * 100);
        if (Y) {
          if (0 === aa) {
            if ("hidden" != this.style.visibility) {
              this.style.visibility = "hidden"
            }
          } else {
            if ("visible" != this.style.visibility) {
              this.style.visibility = "visible"
            }
          }
        }
        if (O.browser.ieMode && O.browser.ieMode < 9) {
          if (!isNaN(aa)) {
            if (!~this.style.filter.indexOf("Alpha")) {
              this.style.filter += " progid:DXImageTransform.Microsoft.Alpha(Opacity=" + aa + ")"
            } else {
              this.style.filter = this.style.filter.replace(/Opacity=\d*/i, "Opacity=" + aa)
            }
          } else {
            this.style.filter = this.style.filter.replace(/progid:DXImageTransform.Microsoft.Alpha\(Opacity=\d*\)/i, "").jTrim();
            if ("" === this.style.filter) {
              this.style.removeAttribute("filter")
            }
          }
        }
        return this
      }, setProps: function (Y) {
        for (var Z in Y) {
          if ("class" === Z) {
            this.jAddClass("" + Y[Z])
          } else {
            this.setAttribute(Z, "" + Y[Z])
          }
        }
        return this
      }, jGetTransitionDuration: function () {
        var Z = 0, Y = 0;
        Z = this.jGetCss("transition-duration");
        Y = this.jGetCss("transition-delay");
        Z = Z.indexOf("ms") > -1 ? parseFloat(Z) : Z.indexOf("s") > -1 ? parseFloat(Z) * 1000 : 0;
        Y = Y.indexOf("ms") > -1 ? parseFloat(Y) : Y.indexOf("s") > -1 ? parseFloat(Y) * 1000 : 0;
        return Z + Y
      }, hide: function () {
        return this.jSetCss({display: "none", visibility: "hidden"})
      }, show: function () {
        return this.jSetCss({display: "", visibility: "visible"})
      }, jGetSize: function () {
        return {width: this.offsetWidth, height: this.offsetHeight}
      }, getInnerSize: function (Z) {
        var Y = this.jGetSize();
        Y.width -= (parseFloat(this.jGetCss("border-left-width") || 0) + parseFloat(this.jGetCss("border-right-width") || 0));
        Y.height -= (parseFloat(this.jGetCss("border-top-width") || 0) + parseFloat(this.jGetCss("border-bottom-width") || 0));
        if (!Z) {
          Y.width -= (parseFloat(this.jGetCss("padding-left") || 0) + parseFloat(this.jGetCss("padding-right") || 0));
          Y.height -= (parseFloat(this.jGetCss("padding-top") || 0) + parseFloat(this.jGetCss("padding-bottom") || 0))
        }
        return Y
      }, jGetScroll: function () {
        return {top: this.scrollTop, left: this.scrollLeft}
      }, jGetFullScroll: function () {
        var Y = this, Z = {top: 0, left: 0};
        do {
          Z.left += Y.scrollLeft || 0;
          Z.top += Y.scrollTop || 0;
          Y = Y.parentNode
        } while (Y);
        return Z
      }, jGetPosition: function () {
        var ac = this, Z = 0, ab = 0;
        if (O.defined(document.documentElement.getBoundingClientRect)) {
          var Y = this.getBoundingClientRect(), aa = O.$(document).jGetScroll(), ad = O.browser.getDoc();
          return {top: Y.top + aa.y - ad.clientTop, left: Y.left + aa.x - ad.clientLeft}
        }
        do {
          Z += ac.offsetLeft || 0;
          ab += ac.offsetTop || 0;
          ac = ac.offsetParent
        } while (ac && !(/^(?:body|html)$/i).test(ac.tagName));
        return {top: ab, left: Z}
      }, jGetRect: function () {
        var Z = this.jGetPosition();
        var Y = this.jGetSize();
        return {top: Z.top, bottom: Z.top + Y.height, left: Z.left, right: Z.left + Y.width}
      }, changeContent: function (Z) {
        try {
          this.innerHTML = Z
        } catch (Y) {
          this.innerText = Z
        }
        return this
      }, jRemove: function () {
        return (this.parentNode) ? this.parentNode.removeChild(this) : this
      }, kill: function () {
        O.$A(this.childNodes).jEach(function (Y) {
          if (3 == Y.nodeType || 8 == Y.nodeType) {
            return
          }
          O.$(Y).kill()
        });
        this.jRemove();
        this.jClearEvents();
        if (this.$J_UUID) {
          O.storage[this.$J_UUID] = null;
          delete O.storage[this.$J_UUID]
        }
        return null
      }, append: function (aa, Z) {
        Z = Z || "bottom";
        var Y = this.firstChild;
        ("top" == Z && Y) ? this.insertBefore(aa, Y) : this.appendChild(aa);
        return this
      }, jAppendTo: function (aa, Z) {
        var Y = O.$(aa).append(this, Z);
        return this
      }, enclose: function (Y) {
        this.append(Y.parentNode.replaceChild(this, Y));
        return this
      }, hasChild: function (Y) {
        if ("element" !== O.jTypeOf("string" == O.jTypeOf(Y) ? Y = document.getElementById(Y) : Y)) {
          return false
        }
        return (this == Y) ? false : (this.contains && !(O.browser.webkit419)) ? (this.contains(Y)) : (this.compareDocumentPosition) ? !!(this.compareDocumentPosition(Y) & 16) : O.$A(this.byTag(Y.tagName)).contains(Y)
      }
    };
    O.Element.jGetStyle = O.Element.jGetCss;
    O.Element.jSetStyle = O.Element.jSetCss;
    if (!window.Element) {
      window.Element = O.$F;
      if (O.browser.engine.webkit) {
        window.document.createElement("iframe")
      }
      window.Element.prototype = (O.browser.engine.webkit) ? window["[[DOMElement.prototype]]"] : {}
    }
    O.implement(window.Element, {$J_TYPE: "element"});
    O.Doc = {
      jGetSize: function () {
        if (O.browser.touchScreen || O.browser.presto925 || O.browser.webkit419) {
          return {width: window.innerWidth, height: window.innerHeight}
        }
        return {width: O.browser.getDoc().clientWidth, height: O.browser.getDoc().clientHeight}
      }, jGetScroll: function () {
        return {x: window.pageXOffset || O.browser.getDoc().scrollLeft, y: window.pageYOffset || O.browser.getDoc().scrollTop}
      }, jGetFullSize: function () {
        var Y = this.jGetSize();
        return {width: Math.max(O.browser.getDoc().scrollWidth, Y.width), height: Math.max(O.browser.getDoc().scrollHeight, Y.height)}
      }
    };
    O.extend(document, {$J_TYPE: "document"});
    O.extend(window, {$J_TYPE: "window"});
    O.extend([O.Element, O.Doc], {
      jFetch: function (ab, Z) {
        var Y = O.getStorage(this.$J_UUID), aa = Y[ab];
        if (undefined !== Z && undefined === aa) {
          aa = Y[ab] = Z
        }
        return (O.defined(aa) ? aa : null)
      }, jStore: function (aa, Z) {
        var Y = O.getStorage(this.$J_UUID);
        Y[aa] = Z;
        return this
      }, jDel: function (Z) {
        var Y = O.getStorage(this.$J_UUID);
        delete Y[Z];
        return this
      }
    });
    if (!(window.HTMLElement && window.HTMLElement.prototype && window.HTMLElement.prototype.getElementsByClassName)) {
      O.extend([O.Element, O.Doc], {
        getElementsByClassName: function (Y) {
          return O.$A(this.getElementsByTagName("*")).filter(function (aa) {
            try {
              return (1 == aa.nodeType && aa.className.has(Y, " "))
            } catch (Z) {
            }
          })
        }
      })
    }
    O.extend([O.Element, O.Doc], {
      byClass: function () {
        return this.getElementsByClassName(arguments[0])
      }, byTag: function () {
        return this.getElementsByTagName(arguments[0])
      }
    });
    if (O.browser.fullScreen.capable && !document.requestFullScreen) {
      O.Element.requestFullScreen = function () {
        O.browser.fullScreen.request(this)
      }
    }
    O.Event = {
      $J_TYPE: "event", isQueueStopped: O.$false, stop: function () {
        return this.stopDistribution().stopDefaults()
      }, stopDistribution: function () {
        if (this.stopPropagation) {
          this.stopPropagation()
        } else {
          this.cancelBubble = true
        }
        return this
      }, stopDefaults: function () {
        if (this.preventDefault) {
          this.preventDefault()
        } else {
          this.returnValue = false
        }
        return this
      }, stopQueue: function () {
        this.isQueueStopped = O.$true;
        return this
      }, getClientXY: function () {
        var Z, Y;
        Z = ((/touch/i).test(this.type)) ? this.changedTouches[0] : this;
        return (!O.defined(Z)) ? {x: 0, y: 0} : {x: Z.clientX, y: Z.clientY}
      }, jGetPageXY: function () {
        var Z, Y;
        Z = ((/touch/i).test(this.type)) ? this.changedTouches[0] : this;
        return (!O.defined(Z)) ? {x: 0, y: 0} : {x: Z.pageX || Z.clientX + O.browser.getDoc().scrollLeft, y: Z.pageY || Z.clientY + O.browser.getDoc().scrollTop}
      }, getTarget: function () {
        var Y = this.target || this.srcElement;
        while (Y && 3 == Y.nodeType) {
          Y = Y.parentNode
        }
        return Y
      }, getRelated: function () {
        var Z = null;
        switch (this.type) {
          case"mouseover":
          case"pointerover":
          case"MSPointerOver":
            Z = this.relatedTarget || this.fromElement;
            break;
          case"mouseout":
          case"pointerout":
          case"MSPointerOut":
            Z = this.relatedTarget || this.toElement;
            break;
          default:
            return Z
        }
        try {
          while (Z && 3 == Z.nodeType) {
            Z = Z.parentNode
          }
        } catch (Y) {
          Z = null
        }
        return Z
      }, getButton: function () {
        if (!this.which && this.button !== undefined) {
          return (this.button & 1 ? 1 : (this.button & 2 ? 3 : (this.button & 4 ? 2 : 0)))
        }
        return this.which
      }, isTouchEvent: function () {
        return (this.pointerType && ("touch" === this.pointerType || this.pointerType === this.MSPOINTER_TYPE_TOUCH)) || (/touch/i).test(this.type)
      }, isPrimaryTouch: function () {
        return this.pointerType ? (("touch" === this.pointerType || this.MSPOINTER_TYPE_TOUCH === this.pointerType) && this.isPrimary) : 1 === this.changedTouches.length && (this.targetTouches.length ? this.targetTouches[0].identifier == this.changedTouches[0].identifier : true)
      }
    };
    O._event_add_ = "addEventListener";
    O._event_del_ = "removeEventListener";
    O._event_prefix_ = "";
    if (!document.addEventListener) {
      O._event_add_ = "attachEvent";
      O._event_del_ = "detachEvent";
      O._event_prefix_ = "on"
    }
    O.Event.Custom = {
      type: "",
      x: null,
      y: null,
      timeStamp: null,
      button: null,
      target: null,
      relatedTarget: null,
      $J_TYPE: "event.custom",
      isQueueStopped: O.$false,
      events: O.$([]),
      pushToEvents: function (Y) {
        var Z = Y;
        this.events.push(Z)
      },
      stop: function () {
        return this.stopDistribution().stopDefaults()
      },
      stopDistribution: function () {
        this.events.jEach(function (Z) {
          try {
            Z.stopDistribution()
          } catch (Y) {
          }
        });
        return this
      },
      stopDefaults: function () {
        this.events.jEach(function (Z) {
          try {
            Z.stopDefaults()
          } catch (Y) {
          }
        });
        return this
      },
      stopQueue: function () {
        this.isQueueStopped = O.$true;
        return this
      },
      getClientXY: function () {
        return {x: this.clientX, y: this.clientY}
      },
      jGetPageXY: function () {
        return {x: this.x, y: this.y}
      },
      getTarget: function () {
        return this.target
      },
      getRelated: function () {
        return this.relatedTarget
      },
      getButton: function () {
        return this.button
      },
      getOriginalTarget: function () {
        return this.events.length > 0 ? this.events[0].getTarget() : undefined
      }
    };
    O.extend([O.Element, O.Doc], {
      jAddEvent: function (aa, ac, ad, ag) {
        var af, Y, ab, ae, Z;
        if ("string" == O.jTypeOf(aa)) {
          Z = aa.split(" ");
          if (Z.length > 1) {
            aa = Z
          }
        }
        if (O.jTypeOf(aa) == "array") {
          O.$(aa).jEach(this.jAddEvent.jBindAsEvent(this, ac, ad, ag));
          return this
        }
        if (!aa || !ac || O.jTypeOf(aa) != "string" || O.jTypeOf(ac) != "function") {
          return this
        }
        if (aa == "domready" && O.browser.ready) {
          ac.call(this);
          return this
        }
        aa = V[aa] || aa;
        ad = parseInt(ad || 50);
        if (!ac.$J_EUID) {
          ac.$J_EUID = Math.floor(Math.random() * O.now())
        }
        af = O.Doc.jFetch.call(this, "_EVENTS_", {});
        Y = af[aa];
        if (!Y) {
          af[aa] = Y = O.$([]);
          ab = this;
          if (O.Event.Custom[aa]) {
            O.Event.Custom[aa].handler.add.call(this, ag)
          } else {
            Y.handle = function (ah) {
              ah = O.extend(ah || window.e, {$J_TYPE: "event"});
              O.Doc.jCallEvent.call(ab, aa, O.$(ah))
            };
            this[O._event_add_](O._event_prefix_ + aa, Y.handle, false)
          }
        }
        ae = {type: aa, fn: ac, priority: ad, euid: ac.$J_EUID};
        Y.push(ae);
        Y.sort(function (ai, ah) {
          return ai.priority - ah.priority
        });
        return this
      }, jRemoveEvent: function (ae) {
        var ac = O.Doc.jFetch.call(this, "_EVENTS_", {}), aa, Y, Z, af, ad, ab;
        ad = arguments.length > 1 ? arguments[1] : -100;
        if ("string" == O.jTypeOf(ae)) {
          ab = ae.split(" ");
          if (ab.length > 1) {
            ae = ab
          }
        }
        if (O.jTypeOf(ae) == "array") {
          O.$(ae).jEach(this.jRemoveEvent.jBindAsEvent(this, ad));
          return this
        }
        ae = V[ae] || ae;
        if (!ae || O.jTypeOf(ae) != "string" || !ac || !ac[ae]) {
          return this
        }
        aa = ac[ae] || [];
        for (Z = 0; Z < aa.length; Z++) {
          Y = aa[Z];
          if (-100 == ad || !!ad && ad.$J_EUID === Y.euid) {
            af = aa.splice(Z--, 1)
          }
        }
        if (0 === aa.length) {
          if (O.Event.Custom[ae]) {
            O.Event.Custom[ae].handler.jRemove.call(this)
          } else {
            this[O._event_del_](O._event_prefix_ + ae, aa.handle, false)
          }
          delete ac[ae]
        }
        return this
      }, jCallEvent: function (ac, ae) {
        var ab = O.Doc.jFetch.call(this, "_EVENTS_", {}), aa, Y, Z;
        ac = V[ac] || ac;
        if (!ac || O.jTypeOf(ac) != "string" || !ab || !ab[ac]) {
          return this
        }
        try {
          ae = O.extend(ae || {}, {type: ac})
        } catch (ad) {
        }
        if (undefined === ae.timeStamp) {
          ae.timeStamp = O.now()
        }
        aa = ab[ac] || [];
        for (Z = 0; Z < aa.length && !(ae.isQueueStopped && ae.isQueueStopped()); Z++) {
          aa[Z].fn.call(this, ae)
        }
      }, jRaiseEvent: function (Z, Y) {
        var ac = ("domready" == Z) ? false : true, ab = this, aa;
        Z = V[Z] || Z;
        if (!ac) {
          O.Doc.jCallEvent.call(this, Z);
          return this
        }
        if (ab === document && document.createEvent && !ab.dispatchEvent) {
          ab = document.documentElement
        }
        if (document.createEvent) {
          aa = document.createEvent(Z);
          aa.initEvent(Y, true, true)
        } else {
          aa = document.createEventObject();
          aa.eventType = Z
        }
        if (document.createEvent) {
          ab.dispatchEvent(aa)
        } else {
          ab.fireEvent("on" + Y, aa)
        }
        return aa
      }, jClearEvents: function () {
        var Z = O.Doc.jFetch.call(this, "_EVENTS_");
        if (!Z) {
          return this
        }
        for (var Y in Z) {
          O.Doc.jRemoveEvent.call(this, Y)
        }
        O.Doc.jDel.call(this, "_EVENTS_");
        return this
      }
    });
    (function (Y) {
      if ("complete" === document.readyState) {
        return Y.browser.onready.jDelay(1)
      }
      if (Y.browser.webkit && Y.browser.version < 420) {
        (function () {
          (Y.$(["loaded", "complete"]).contains(document.readyState)) ? Y.browser.onready() : arguments.callee.jDelay(50)
        })()
      } else {
        if (Y.browser.trident && Y.browser.ieMode < 9 && window == top) {
          (function () {
            (Y.$try(function () {
              Y.browser.getDoc().doScroll("left");
              return true
            })) ? Y.browser.onready() : arguments.callee.jDelay(50)
          })()
        } else {
          Y.Doc.jAddEvent.call(Y.$(document), "DOMContentLoaded", Y.browser.onready);
          Y.Doc.jAddEvent.call(Y.$(window), "load", Y.browser.onready)
        }
      }
    })(U);
    O.Class = function () {
      var ac = null, Z = O.$A(arguments);
      if ("class" == O.jTypeOf(Z[0])) {
        ac = Z.shift()
      }
      var Y = function () {
        for (var af in this) {
          this[af] = O.detach(this[af])
        }
        if (this.constructor.$parent) {
          this.$parent = {};
          var ah = this.constructor.$parent;
          for (var ag in ah) {
            var ae = ah[ag];
            switch (O.jTypeOf(ae)) {
              case"function":
                this.$parent[ag] = O.Class.wrap(this, ae);
                break;
              case"object":
                this.$parent[ag] = O.detach(ae);
                break;
              case"array":
                this.$parent[ag] = O.detach(ae);
                break
            }
          }
        }
        var ad = (this.init) ? this.init.apply(this, arguments) : this;
        delete this.caller;
        return ad
      };
      if (!Y.prototype.init) {
        Y.prototype.init = O.$F
      }
      if (ac) {
        var ab = function () {
        };
        ab.prototype = ac.prototype;
        Y.prototype = new ab;
        Y.$parent = {};
        for (var aa in ac.prototype) {
          Y.$parent[aa] = ac.prototype[aa]
        }
      } else {
        Y.$parent = null
      }
      Y.constructor = O.Class;
      Y.prototype.constructor = Y;
      O.extend(Y.prototype, Z[0]);
      O.extend(Y, {$J_TYPE: "class"});
      return Y
    };
    U.Class.wrap = function (Y, Z) {
      return function () {
        var ab = this.caller;
        var aa = Z.apply(Y, arguments);
        return aa
      }
    };
    (function (ab) {
      var aa = ab.$;
      var Y = 5, Z = 300;
      ab.Event.Custom.btnclick = new ab.Class(ab.extend(ab.Event.Custom, {
        type: "btnclick", init: function (ae, ad) {
          var ac = ad.jGetPageXY();
          this.x = ac.x;
          this.y = ac.y;
          this.clientX = ad.clientX;
          this.clientY = ad.clientY;
          this.timeStamp = ad.timeStamp;
          this.button = ad.getButton();
          this.target = ae;
          this.pushToEvents(ad)
        }
      }));
      ab.Event.Custom.btnclick.handler = {
        options: {threshold: Z, button: 1}, add: function (ac) {
          this.jStore("event:btnclick:options", ab.extend(ab.detach(ab.Event.Custom.btnclick.handler.options), ac || {}));
          this.jAddEvent("mousedown", ab.Event.Custom.btnclick.handler.handle, 1);
          this.jAddEvent("mouseup", ab.Event.Custom.btnclick.handler.handle, 1);
          this.jAddEvent("click", ab.Event.Custom.btnclick.handler.onclick, 1);
          if (ab.browser.trident && ab.browser.ieMode < 9) {
            this.jAddEvent("dblclick", ab.Event.Custom.btnclick.handler.handle, 1)
          }
        }, jRemove: function () {
          this.jRemoveEvent("mousedown", ab.Event.Custom.btnclick.handler.handle);
          this.jRemoveEvent("mouseup", ab.Event.Custom.btnclick.handler.handle);
          this.jRemoveEvent("click", ab.Event.Custom.btnclick.handler.onclick);
          if (ab.browser.trident && ab.browser.ieMode < 9) {
            this.jRemoveEvent("dblclick", ab.Event.Custom.btnclick.handler.handle)
          }
        }, onclick: function (ac) {
          ac.stopDefaults()
        }, handle: function (af) {
          var ae, ac, ad;
          ac = this.jFetch("event:btnclick:options");
          if (af.type != "dblclick" && af.getButton() != ac.button) {
            return
          }
          if (this.jFetch("event:btnclick:ignore")) {
            this.jDel("event:btnclick:ignore");
            return
          }
          if ("mousedown" == af.type) {
            ae = new ab.Event.Custom.btnclick(this, af);
            this.jStore("event:btnclick:btnclickEvent", ae)
          } else {
            if ("mouseup" == af.type) {
              ae = this.jFetch("event:btnclick:btnclickEvent");
              if (!ae) {
                return
              }
              ad = af.jGetPageXY();
              this.jDel("event:btnclick:btnclickEvent");
              ae.pushToEvents(af);
              if (af.timeStamp - ae.timeStamp <= ac.threshold && Math.sqrt(Math.pow(ad.x - ae.x, 2) + Math.pow(ad.y - ae.y, 2)) <= Y) {
                this.jCallEvent("btnclick", ae)
              }
              document.jCallEvent("mouseup", af)
            } else {
              if (af.type == "dblclick") {
                ae = new ab.Event.Custom.btnclick(this, af);
                this.jCallEvent("btnclick", ae)
              }
            }
          }
        }
      }
    })(U);
    (function (Z) {
      var Y = Z.$;
      Z.Event.Custom.mousedrag = new Z.Class(Z.extend(Z.Event.Custom, {
        type: "mousedrag", state: "dragstart", dragged: false, init: function (ad, ac, ab) {
          var aa = ac.jGetPageXY();
          this.x = aa.x;
          this.y = aa.y;
          this.clientX = ac.clientX;
          this.clientY = ac.clientY;
          this.timeStamp = ac.timeStamp;
          this.button = ac.getButton();
          this.target = ad;
          this.pushToEvents(ac);
          this.state = ab
        }
      }));
      Z.Event.Custom.mousedrag.handler = {
        add: function () {
          var ab = Z.Event.Custom.mousedrag.handler.handleMouseMove.jBindAsEvent(this), aa = Z.Event.Custom.mousedrag.handler.handleMouseUp.jBindAsEvent(this);
          this.jAddEvent("mousedown", Z.Event.Custom.mousedrag.handler.handleMouseDown, 1);
          this.jAddEvent("mouseup", Z.Event.Custom.mousedrag.handler.handleMouseUp, 1);
          document.jAddEvent("mousemove", ab, 1);
          document.jAddEvent("mouseup", aa, 1);
          this.jStore("event:mousedrag:listeners:document:move", ab);
          this.jStore("event:mousedrag:listeners:document:end", aa)
        }, jRemove: function () {
          this.jRemoveEvent("mousedown", Z.Event.Custom.mousedrag.handler.handleMouseDown);
          this.jRemoveEvent("mouseup", Z.Event.Custom.mousedrag.handler.handleMouseUp);
          Y(document).jRemoveEvent("mousemove", this.jFetch("event:mousedrag:listeners:document:move") || Z.$F);
          Y(document).jRemoveEvent("mouseup", this.jFetch("event:mousedrag:listeners:document:end") || Z.$F);
          this.jDel("event:mousedrag:listeners:document:move");
          this.jDel("event:mousedrag:listeners:document:end")
        }, handleMouseDown: function (ab) {
          var aa;
          if (1 != ab.getButton()) {
            return
          }
          aa = new Z.Event.Custom.mousedrag(this, ab, "dragstart");
          this.jStore("event:mousedrag:dragstart", aa)
        }, handleMouseUp: function (ab) {
          var aa;
          aa = this.jFetch("event:mousedrag:dragstart");
          if (!aa) {
            return
          }
          ab.stopDefaults();
          aa = new Z.Event.Custom.mousedrag(this, ab, "dragend");
          this.jDel("event:mousedrag:dragstart");
          this.jCallEvent("mousedrag", aa)
        }, handleMouseMove: function (ab) {
          var aa;
          aa = this.jFetch("event:mousedrag:dragstart");
          if (!aa) {
            return
          }
          ab.stopDefaults();
          if (!aa.dragged) {
            aa.dragged = true;
            this.jCallEvent("mousedrag", aa)
          }
          aa = new Z.Event.Custom.mousedrag(this, ab, "dragmove");
          this.jCallEvent("mousedrag", aa)
        }
      }
    })(U);
    (function (Z) {
      var Y = Z.$;
      Z.Event.Custom.dblbtnclick = new Z.Class(Z.extend(Z.Event.Custom, {
        type: "dblbtnclick", timedout: false, tm: null, init: function (ac, ab) {
          var aa = ab.jGetPageXY();
          this.x = aa.x;
          this.y = aa.y;
          this.clientX = ab.clientX;
          this.clientY = ab.clientY;
          this.timeStamp = ab.timeStamp;
          this.button = ab.getButton();
          this.target = ac;
          this.pushToEvents(ab)
        }
      }));
      Z.Event.Custom.dblbtnclick.handler = {
        options: {threshold: 200}, add: function (aa) {
          this.jStore("event:dblbtnclick:options", Z.extend(Z.detach(Z.Event.Custom.dblbtnclick.handler.options), aa || {}));
          this.jAddEvent("btnclick", Z.Event.Custom.dblbtnclick.handler.handle, 1)
        }, jRemove: function () {
          this.jRemoveEvent("btnclick", Z.Event.Custom.dblbtnclick.handler.handle)
        }, handle: function (ac) {
          var ab, aa;
          ab = this.jFetch("event:dblbtnclick:event");
          aa = this.jFetch("event:dblbtnclick:options");
          if (!ab) {
            ab = new Z.Event.Custom.dblbtnclick(this, ac);
            ab.tm = setTimeout(function () {
              ab.timedout = true;
              ac.isQueueStopped = Z.$false;
              this.jCallEvent("btnclick", ac);
              this.jDel("event:dblbtnclick:event")
            }.jBind(this), aa.threshold + 10);
            this.jStore("event:dblbtnclick:event", ab);
            ac.stopQueue()
          } else {
            clearTimeout(ab.tm);
            this.jDel("event:dblbtnclick:event");
            if (!ab.timedout) {
              ab.pushToEvents(ac);
              ac.stopQueue().stop();
              this.jCallEvent("dblbtnclick", ab)
            } else {
            }
          }
        }
      }
    })(U);
    (function (ae) {
      var ad = ae.$;

      function Y(af) {
        return af.pointerType ? (("touch" === af.pointerType || af.MSPOINTER_TYPE_TOUCH === af.pointerType) && af.isPrimary) : 1 === af.changedTouches.length && (af.targetTouches.length ? af.targetTouches[0].identifier == af.changedTouches[0].identifier : true)
      }

      function aa(af) {
        if (af.pointerType) {
          return ("touch" === af.pointerType || af.MSPOINTER_TYPE_TOUCH === af.pointerType) ? af.pointerId : null
        } else {
          return af.changedTouches[0].identifier
        }
      }

      function ab(af) {
        if (af.pointerType) {
          return ("touch" === af.pointerType || af.MSPOINTER_TYPE_TOUCH === af.pointerType) ? af : null
        } else {
          return af.changedTouches[0]
        }
      }

      ae.Event.Custom.tap = new ae.Class(ae.extend(ae.Event.Custom, {
        type: "tap", id: null, init: function (ag, af) {
          var ah = ab(af);
          this.id = ah.pointerId || ah.identifier;
          this.x = ah.pageX;
          this.y = ah.pageY;
          this.pageX = ah.pageX;
          this.pageY = ah.pageY;
          this.clientX = ah.clientX;
          this.clientY = ah.clientY;
          this.timeStamp = af.timeStamp;
          this.button = 0;
          this.target = ag;
          this.pushToEvents(af)
        }
      }));
      var Z = 10, ac = 200;
      ae.Event.Custom.tap.handler = {
        add: function (af) {
          this.jAddEvent(["touchstart", window.navigator.pointerEnabled ? "pointerdown" : "MSPointerDown"], ae.Event.Custom.tap.handler.onTouchStart, 1);
          this.jAddEvent(["touchend", window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp"], ae.Event.Custom.tap.handler.onTouchEnd, 1);
          this.jAddEvent("click", ae.Event.Custom.tap.handler.onClick, 1)
        }, jRemove: function () {
          this.jRemoveEvent(["touchstart", window.navigator.pointerEnabled ? "pointerdown" : "MSPointerDown"], ae.Event.Custom.tap.handler.onTouchStart);
          this.jRemoveEvent(["touchend", window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp"], ae.Event.Custom.tap.handler.onTouchEnd);
          this.jRemoveEvent("click", ae.Event.Custom.tap.handler.onClick)
        }, onClick: function (af) {
          af.stopDefaults()
        }, onTouchStart: function (af) {
          if (!Y(af)) {
            this.jDel("event:tap:event");
            return
          }
          this.jStore("event:tap:event", new ae.Event.Custom.tap(this, af));
          this.jStore("event:btnclick:ignore", true)
        }, onTouchEnd: function (ai) {
          var ag = ae.now(), ah = this.jFetch("event:tap:event"), af = this.jFetch("event:tap:options");
          if (!ah || !Y(ai)) {
            return
          }
          this.jDel("event:tap:event");
          if (ah.id == aa(ai) && ai.timeStamp - ah.timeStamp <= ac && Math.sqrt(Math.pow(ab(ai).pageX - ah.x, 2) + Math.pow(ab(ai).pageY - ah.y, 2)) <= Z) {
            this.jDel("event:btnclick:btnclickEvent");
            ai.stop();
            ah.pushToEvents(ai);
            this.jCallEvent("tap", ah)
          }
        }
      }
    })(U);
    O.Event.Custom.dbltap = new O.Class(O.extend(O.Event.Custom, {
      type: "dbltap", timedout: false, tm: null, init: function (Z, Y) {
        this.x = Y.x;
        this.y = Y.y;
        this.clientX = Y.clientX;
        this.clientY = Y.clientY;
        this.timeStamp = Y.timeStamp;
        this.button = 0;
        this.target = Z;
        this.pushToEvents(Y)
      }
    }));
    O.Event.Custom.dbltap.handler = {
      options: {threshold: 300}, add: function (Y) {
        this.jStore("event:dbltap:options", O.extend(O.detach(O.Event.Custom.dbltap.handler.options), Y || {}));
        this.jAddEvent("tap", O.Event.Custom.dbltap.handler.handle, 1)
      }, jRemove: function () {
        this.jRemoveEvent("tap", O.Event.Custom.dbltap.handler.handle)
      }, handle: function (aa) {
        var Z, Y;
        Z = this.jFetch("event:dbltap:event");
        Y = this.jFetch("event:dbltap:options");
        if (!Z) {
          Z = new O.Event.Custom.dbltap(this, aa);
          Z.tm = setTimeout(function () {
            Z.timedout = true;
            aa.isQueueStopped = O.$false;
            this.jCallEvent("tap", aa)
          }.jBind(this), Y.threshold + 10);
          this.jStore("event:dbltap:event", Z);
          aa.stopQueue()
        } else {
          clearTimeout(Z.tm);
          this.jDel("event:dbltap:event");
          if (!Z.timedout) {
            Z.pushToEvents(aa);
            aa.stopQueue().stop();
            this.jCallEvent("dbltap", Z)
          } else {
          }
        }
      }
    };
    (function (ad) {
      var ac = ad.$;

      function Y(ae) {
        return ae.pointerType ? (("touch" === ae.pointerType || ae.MSPOINTER_TYPE_TOUCH === ae.pointerType) && ae.isPrimary) : 1 === ae.changedTouches.length && (ae.targetTouches.length ? ae.targetTouches[0].identifier == ae.changedTouches[0].identifier : true)
      }

      function aa(ae) {
        if (ae.pointerType) {
          return ("touch" === ae.pointerType || ae.MSPOINTER_TYPE_TOUCH === ae.pointerType) ? ae.pointerId : null
        } else {
          return ae.changedTouches[0].identifier
        }
      }

      function ab(ae) {
        if (ae.pointerType) {
          return ("touch" === ae.pointerType || ae.MSPOINTER_TYPE_TOUCH === ae.pointerType) ? ae : null
        } else {
          return ae.changedTouches[0]
        }
      }

      var Z = 10;
      ad.Event.Custom.touchdrag = new ad.Class(ad.extend(ad.Event.Custom, {
        type: "touchdrag", state: "dragstart", id: null, dragged: false, init: function (ag, af, ae) {
          var ah = ab(af);
          this.id = ah.pointerId || ah.identifier;
          this.clientX = ah.clientX;
          this.clientY = ah.clientY;
          this.pageX = ah.pageX;
          this.pageY = ah.pageY;
          this.x = ah.pageX;
          this.y = ah.pageY;
          this.timeStamp = af.timeStamp;
          this.button = 0;
          this.target = ag;
          this.pushToEvents(af);
          this.state = ae
        }
      }));
      ad.Event.Custom.touchdrag.handler = {
        add: function () {
          var af = ad.Event.Custom.touchdrag.handler.onTouchMove.jBind(this), ae = ad.Event.Custom.touchdrag.handler.onTouchEnd.jBind(this);
          this.jAddEvent(["touchstart", window.navigator.pointerEnabled ? "pointerdown" : "MSPointerDown"], ad.Event.Custom.touchdrag.handler.onTouchStart, 1);
          this.jAddEvent(["touchend", window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp"], ad.Event.Custom.touchdrag.handler.onTouchEnd, 1);
          this.jAddEvent(["touchmove", window.navigator.pointerEnabled ? "pointermove" : "MSPointerMove"], ad.Event.Custom.touchdrag.handler.onTouchMove, 1);
          this.jStore("event:touchdrag:listeners:document:move", af);
          this.jStore("event:touchdrag:listeners:document:end", ae);
          ac(document).jAddEvent(window.navigator.pointerEnabled ? "pointermove" : "MSPointerMove", af, 1);
          ac(document).jAddEvent(window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp", ae, 1)
        }, jRemove: function () {
          this.jRemoveEvent(["touchstart", window.navigator.pointerEnabled ? "pointerdown" : "MSPointerDown"], ad.Event.Custom.touchdrag.handler.onTouchStart);
          this.jRemoveEvent(["touchend", window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp"], ad.Event.Custom.touchdrag.handler.onTouchEnd);
          this.jRemoveEvent(["touchmove", window.navigator.pointerEnabled ? "pointermove" : "MSPointerMove"], ad.Event.Custom.touchdrag.handler.onTouchMove);
          ac(document).jRemoveEvent(window.navigator.pointerEnabled ? "pointermove" : "MSPointerMove", this.jFetch("event:touchdrag:listeners:document:move") || ad.$F, 1);
          ac(document).jRemoveEvent(window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp", this.jFetch("event:touchdrag:listeners:document:end") || ad.$F, 1);
          this.jDel("event:touchdrag:listeners:document:move");
          this.jDel("event:touchdrag:listeners:document:end")
        }, onTouchStart: function (af) {
          var ae;
          if (!Y(af)) {
            return
          }
          ae = new ad.Event.Custom.touchdrag(this, af, "dragstart");
          this.jStore("event:touchdrag:dragstart", ae)
        }, onTouchEnd: function (af) {
          var ae;
          ae = this.jFetch("event:touchdrag:dragstart");
          if (!ae || !ae.dragged || ae.id != aa(af)) {
            return
          }
          ae = new ad.Event.Custom.touchdrag(this, af, "dragend");
          this.jDel("event:touchdrag:dragstart");
          this.jCallEvent("touchdrag", ae)
        }, onTouchMove: function (af) {
          var ae;
          ae = this.jFetch("event:touchdrag:dragstart");
          if (!ae || !Y(af)) {
            return
          }
          if (ae.id != aa(af)) {
            this.jDel("event:touchdrag:dragstart");
            return
          }
          if (!ae.dragged && Math.sqrt(Math.pow(ab(af).pageX - ae.x, 2) + Math.pow(ab(af).pageY - ae.y, 2)) > Z) {
            ae.dragged = true;
            this.jCallEvent("touchdrag", ae)
          }
          if (!ae.dragged) {
            return
          }
          ae = new ad.Event.Custom.touchdrag(this, af, "dragmove");
          this.jCallEvent("touchdrag", ae)
        }
      }
    })(U);
    O.Event.Custom.touchpinch = new O.Class(O.extend(O.Event.Custom, {
      type: "touchpinch", scale: 1, previousScale: 1, curScale: 1, state: "pinchstart", init: function (Z, Y) {
        this.timeStamp = Y.timeStamp;
        this.button = 0;
        this.target = Z;
        this.x = Y.touches[0].clientX + (Y.touches[1].clientX - Y.touches[0].clientX) / 2;
        this.y = Y.touches[0].clientY + (Y.touches[1].clientY - Y.touches[0].clientY) / 2;
        this._initialDistance = Math.sqrt(Math.pow(Y.touches[0].clientX - Y.touches[1].clientX, 2) + Math.pow(Y.touches[0].clientY - Y.touches[1].clientY, 2));
        this.pushToEvents(Y)
      }, update: function (Y) {
        var Z;
        this.state = "pinchupdate";
        if (Y.changedTouches[0].identifier != this.events[0].touches[0].identifier || Y.changedTouches[1].identifier != this.events[0].touches[1].identifier) {
          return
        }
        Z = Math.sqrt(Math.pow(Y.changedTouches[0].clientX - Y.changedTouches[1].clientX, 2) + Math.pow(Y.changedTouches[0].clientY - Y.changedTouches[1].clientY, 2));
        this.previousScale = this.scale;
        this.scale = Z / this._initialDistance;
        this.curScale = this.scale / this.previousScale;
        this.x = Y.changedTouches[0].clientX + (Y.changedTouches[1].clientX - Y.changedTouches[0].clientX) / 2;
        this.y = Y.changedTouches[0].clientY + (Y.changedTouches[1].clientY - Y.changedTouches[0].clientY) / 2;
        this.pushToEvents(Y)
      }
    }));
    O.Event.Custom.touchpinch.handler = {
      add: function () {
        this.jAddEvent("touchstart", O.Event.Custom.touchpinch.handler.handleTouchStart, 1);
        this.jAddEvent("touchend", O.Event.Custom.touchpinch.handler.handleTouchEnd, 1);
        this.jAddEvent("touchmove", O.Event.Custom.touchpinch.handler.handleTouchMove, 1)
      }, jRemove: function () {
        this.jRemoveEvent("touchstart", O.Event.Custom.touchpinch.handler.handleTouchStart);
        this.jRemoveEvent("touchend", O.Event.Custom.touchpinch.handler.handleTouchEnd);
        this.jRemoveEvent("touchmove", O.Event.Custom.touchpinch.handler.handleTouchMove)
      }, handleTouchStart: function (Z) {
        var Y;
        if (Z.touches.length != 2) {
          return
        }
        Z.stopDefaults();
        Y = new O.Event.Custom.touchpinch(this, Z);
        this.jStore("event:touchpinch:event", Y)
      }, handleTouchEnd: function (Z) {
        var Y;
        Y = this.jFetch("event:touchpinch:event");
        if (!Y) {
          return
        }
        Z.stopDefaults();
        this.jDel("event:touchpinch:event")
      }, handleTouchMove: function (Z) {
        var Y;
        Y = this.jFetch("event:touchpinch:event");
        if (!Y) {
          return
        }
        Z.stopDefaults();
        Y.update(Z);
        this.jCallEvent("touchpinch", Y)
      }
    };
    (function (ad) {
      var ab = ad.$;
      ad.Event.Custom.mousescroll = new ad.Class(ad.extend(ad.Event.Custom, {
        type: "mousescroll", init: function (aj, ai, al, af, ae, ak, ag) {
          var ah = ai.jGetPageXY();
          this.x = ah.x;
          this.y = ah.y;
          this.timeStamp = ai.timeStamp;
          this.target = aj;
          this.delta = al || 0;
          this.deltaX = af || 0;
          this.deltaY = ae || 0;
          this.deltaZ = ak || 0;
          this.deltaFactor = ag || 0;
          this.deltaMode = ai.deltaMode || 0;
          this.isMouse = false;
          this.pushToEvents(ai)
        }
      }));
      var ac, Z;

      function Y() {
        ac = null
      }

      function aa(ae, af) {
        return (ae > 50) || (1 === af && !("win" == ad.browser.platform && ae < 1)) || (0 === ae % 12) || (0 == ae % 4.000244140625)
      }

      ad.Event.Custom.mousescroll.handler = {
        eventType: "onwheel" in document || ad.browser.ieMode > 8 ? "wheel" : "mousewheel", add: function () {
          this.jAddEvent(ad.Event.Custom.mousescroll.handler.eventType, ad.Event.Custom.mousescroll.handler.handle, 1)
        }, jRemove: function () {
          this.jRemoveEvent(ad.Event.Custom.mousescroll.handler.eventType, ad.Event.Custom.mousescroll.handler.handle, 1)
        }, handle: function (aj) {
          var ak = 0, ah = 0, af = 0, ae = 0, ai, ag;
          if (aj.detail) {
            af = aj.detail * -1
          }
          if (aj.wheelDelta !== undefined) {
            af = aj.wheelDelta
          }
          if (aj.wheelDeltaY !== undefined) {
            af = aj.wheelDeltaY
          }
          if (aj.wheelDeltaX !== undefined) {
            ah = aj.wheelDeltaX * -1
          }
          if (aj.deltaY) {
            af = -1 * aj.deltaY
          }
          if (aj.deltaX) {
            ah = aj.deltaX
          }
          if (0 === af && 0 === ah) {
            return
          }
          ak = 0 === af ? ah : af;
          ae = Math.max(Math.abs(af), Math.abs(ah));
          if (!ac || ae < ac) {
            ac = ae
          }
          ai = ak > 0 ? "floor" : "ceil";
          ak = Math[ai](ak / ac);
          ah = Math[ai](ah / ac);
          af = Math[ai](af / ac);
          if (Z) {
            clearTimeout(Z)
          }
          Z = setTimeout(Y, 200);
          ag = new ad.Event.Custom.mousescroll(this, aj, ak, ah, af, 0, ac);
          ag.isMouse = aa(ac, aj.deltaMode || 0);
          this.jCallEvent("mousescroll", ag)
        }
      }
    })(U);
    O.win = O.$(window);
    O.doc = O.$(document);
    return U
  })();
  (function (K) {
    if (!K) {
      throw"MagicJS not found"
    }
    var J = K.$;
    var I = window.URL || window.webkitURL || null;
    y.ImageLoader = new K.Class({
      img: null,
      ready: false,
      options: {onprogress: K.$F, onload: K.$F, onabort: K.$F, onerror: K.$F, oncomplete: K.$F, onxhrerror: K.$F, xhr: false, progressiveLoad: true},
      size: null,
      _timer: null,
      loadedBytes: 0,
      _handlers: {
        onprogress: function (L) {
          if (L.target && (200 === L.target.status || 304 === L.target.status) && L.lengthComputable) {
            this.options.onprogress.jBind(null, (L.loaded - (this.options.progressiveLoad ? this.loadedBytes : 0)) / L.total).jDelay(1);
            this.loadedBytes = L.loaded
          }
        }, onload: function (L) {
          if (L) {
            J(L).stop()
          }
          this._unbind();
          if (this.ready) {
            return
          }
          this.ready = true;
          this._cleanup();
          !this.options.xhr && this.options.onprogress.jBind(null, 1).jDelay(1);
          this.options.onload.jBind(null, this).jDelay(1);
          this.options.oncomplete.jBind(null, this).jDelay(1)
        }, onabort: function (L) {
          if (L) {
            J(L).stop()
          }
          this._unbind();
          this.ready = false;
          this._cleanup();
          this.options.onabort.jBind(null, this).jDelay(1);
          this.options.oncomplete.jBind(null, this).jDelay(1)
        }, onerror: function (L) {
          if (L) {
            J(L).stop()
          }
          this._unbind();
          this.ready = false;
          this._cleanup();
          this.options.onerror.jBind(null, this).jDelay(1);
          this.options.oncomplete.jBind(null, this).jDelay(1)
        }
      },
      _bind: function () {
        J(["load", "abort", "error"]).jEach(function (L) {
          this.img.jAddEvent(L, this._handlers["on" + L].jBindAsEvent(this).jDefer(1))
        }, this)
      },
      _unbind: function () {
        if (this._timer) {
          try {
            clearTimeout(this._timer)
          } catch (L) {
          }
          this._timer = null
        }
        J(["load", "abort", "error"]).jEach(function (M) {
          this.img.jRemoveEvent(M)
        }, this)
      },
      _cleanup: function () {
        this.jGetSize();
        if (this.img.jFetch("new")) {
          var L = this.img.parentNode;
          this.img.jRemove().jDel("new").jSetCss({position: "static", top: "auto"});
          L.kill()
        }
      },
      loadBlob: function (M) {
        var N = new XMLHttpRequest(), L;
        J(["abort", "progress"]).jEach(function (O) {
          N["on" + O] = J(function (P) {
            this._handlers["on" + O].call(this, P)
          }).jBind(this)
        }, this);
        N.onerror = J(function () {
          this.options.onxhrerror.jBind(null, this).jDelay(1);
          this.options.xhr = false;
          this._bind();
          this.img.src = M
        }).jBind(this);
        N.onload = J(function () {
          if (200 !== N.status && 304 !== N.status) {
            this._handlers.onerror.call(this);
            return
          }
          L = N.response;
          this._bind();
          if (I && !K.browser.trident && !("ios" === K.browser.platform && K.browser.version < 537)) {
            this.img.setAttribute("src", I.createObjectURL(L))
          } else {
            this.img.src = M
          }
        }).jBind(this);
        N.open("GET", M);
        N.responseType = "blob";
        N.send()
      },
      init: function (M, L) {
        this.options = K.extend(this.options, L);
        this.img = J(M) || K.$new("img", {}, {"max-width": "none", "max-height": "none"}).jAppendTo(K.$new("div").jAddClass("magic-temporary-img").jSetCss({
            position: "absolute",
            top: -10000,
            width: 10,
            height: 10,
            overflow: "hidden"
          }).jAppendTo(document.body)).jStore("new", true);
        if (K.browser.features.xhr2 && this.options.xhr && "string" == K.jTypeOf(M)) {
          this.loadBlob(M);
          return
        }
        var N = function () {
          if (this.isReady()) {
            this._handlers.onload.call(this)
          } else {
            this._handlers.onerror.call(this)
          }
          N = null
        }.jBind(this);
        this._bind();
        if ("string" == K.jTypeOf(M)) {
          this.img.src = M
        } else {
          if (K.browser.trident && 5 == K.browser.version && K.browser.ieMode < 9) {
            this.img.onreadystatechange = function () {
              if (/loaded|complete/.test(this.img.readyState)) {
                this.img.onreadystatechange = null;
                N && N()
              }
            }.jBind(this)
          }
          this.img.src = M.getAttribute("src")
        }
        this.img && this.img.complete && N && (this._timer = N.jDelay(100))
      },
      destroy: function () {
        this._unbind();
        this._cleanup();
        this.ready = false;
        return this
      },
      isReady: function () {
        var L = this.img;
        return (L.naturalWidth) ? (L.naturalWidth > 0) : (L.readyState) ? ("complete" == L.readyState) : L.width > 0
      },
      jGetSize: function () {
        return this.size || (this.size = {width: this.img.naturalWidth || this.img.width, height: this.img.naturalHeight || this.img.height})
      }
    })
  })(y);
  (function (J) {
    if (!J) {
      throw"MagicJS not found"
    }
    if (J.FX) {
      return
    }
    var I = J.$;
    J.FX = new J.Class({
      init: function (L, K) {
        var M;
        this.el = J.$(L);
        this.options = J.extend(this.options, K);
        this.timer = false;
        this.easeFn = this.cubicBezierAtTime;
        M = J.FX.Transition[this.options.transition] || this.options.transition;
        if ("function" === J.jTypeOf(M)) {
          this.easeFn = M
        } else {
          this.cubicBezier = this.parseCubicBezier(M) || this.parseCubicBezier("ease")
        }
        if ("string" == J.jTypeOf(this.options.cycles)) {
          this.options.cycles = "infinite" === this.options.cycles ? Infinity : parseInt(this.options.cycles) || 1
        }
      },
      options: {
        fps: 60,
        duration: 600,
        transition: "ease",
        cycles: 1,
        direction: "normal",
        onStart: J.$F,
        onComplete: J.$F,
        onBeforeRender: J.$F,
        onAfterRender: J.$F,
        forceAnimation: false,
        roundCss: false
      },
      styles: null,
      cubicBezier: null,
      easeFn: null,
      setTransition: function (K) {
        this.options.transition = K;
        K = J.FX.Transition[this.options.transition] || this.options.transition;
        if ("function" === J.jTypeOf(K)) {
          this.easeFn = K
        } else {
          this.easeFn = this.cubicBezierAtTime;
          this.cubicBezier = this.parseCubicBezier(K) || this.parseCubicBezier("ease")
        }
      },
      start: function (M) {
        var K = /\%$/, L;
        this.styles = M;
        this.cycle = 0;
        this.state = 0;
        this.curFrame = 0;
        this.pStyles = {};
        this.alternate = "alternate" === this.options.direction || "alternate-reverse" === this.options.direction;
        this.continuous = "continuous" === this.options.direction || "continuous-reverse" === this.options.direction;
        for (L in this.styles) {
          K.test(this.styles[L][0]) && (this.pStyles[L] = true);
          if ("reverse" === this.options.direction || "alternate-reverse" === this.options.direction || "continuous-reverse" === this.options.direction) {
            this.styles[L].reverse()
          }
        }
        this.startTime = J.now();
        this.finishTime = this.startTime + this.options.duration;
        this.options.onStart.call();
        if (0 === this.options.duration) {
          this.render(1);
          this.options.onComplete.call()
        } else {
          this.loopBind = this.loop.jBind(this);
          if (!this.options.forceAnimation && J.browser.features.requestAnimationFrame) {
            this.timer = J.browser.requestAnimationFrame.call(window, this.loopBind)
          } else {
            this.timer = this.loopBind.interval(Math.round(1000 / this.options.fps))
          }
        }
        return this
      },
      stopAnimation: function () {
        if (this.timer) {
          if (!this.options.forceAnimation && J.browser.features.requestAnimationFrame && J.browser.cancelAnimationFrame) {
            J.browser.cancelAnimationFrame.call(window, this.timer)
          } else {
            clearInterval(this.timer)
          }
          this.timer = false
        }
      },
      stop: function (K) {
        K = J.defined(K) ? K : false;
        this.stopAnimation();
        if (K) {
          this.render(1);
          this.options.onComplete.jDelay(10)
        }
        return this
      },
      calc: function (M, L, K) {
        M = parseFloat(M);
        L = parseFloat(L);
        return (L - M) * K + M
      },
      loop: function () {
        var L = J.now(), K = (L - this.startTime) / this.options.duration, M = Math.floor(K);
        if (L >= this.finishTime && M >= this.options.cycles) {
          this.stopAnimation();
          this.render(1);
          this.options.onComplete.jDelay(10);
          return this
        }
        if (this.alternate && this.cycle < M) {
          for (var N in this.styles) {
            this.styles[N].reverse()
          }
        }
        this.cycle = M;
        if (!this.options.forceAnimation && J.browser.features.requestAnimationFrame) {
          this.timer = J.browser.requestAnimationFrame.call(window, this.loopBind)
        }
        this.render((this.continuous ? M : 0) + this.easeFn(K % 1))
      },
      render: function (K) {
        var L = {}, N = K;
        for (var M in this.styles) {
          if ("opacity" === M) {
            L[M] = Math.round(this.calc(this.styles[M][0], this.styles[M][1], K) * 100) / 100
          } else {
            L[M] = this.calc(this.styles[M][0], this.styles[M][1], K);
            this.pStyles[M] && (L[M] += "%")
          }
        }
        this.options.onBeforeRender(L, this.el);
        this.set(L);
        this.options.onAfterRender(L, this.el)
      },
      set: function (K) {
        return this.el.jSetCss(K)
      },
      parseCubicBezier: function (K) {
        var L, M = null;
        if ("string" !== J.jTypeOf(K)) {
          return null
        }
        switch (K) {
          case"linear":
            M = I([0, 0, 1, 1]);
            break;
          case"ease":
            M = I([0.25, 0.1, 0.25, 1]);
            break;
          case"ease-in":
            M = I([0.42, 0, 1, 1]);
            break;
          case"ease-out":
            M = I([0, 0, 0.58, 1]);
            break;
          case"ease-in-out":
            M = I([0.42, 0, 0.58, 1]);
            break;
          case"easeInSine":
            M = I([0.47, 0, 0.745, 0.715]);
            break;
          case"easeOutSine":
            M = I([0.39, 0.575, 0.565, 1]);
            break;
          case"easeInOutSine":
            M = I([0.445, 0.05, 0.55, 0.95]);
            break;
          case"easeInQuad":
            M = I([0.55, 0.085, 0.68, 0.53]);
            break;
          case"easeOutQuad":
            M = I([0.25, 0.46, 0.45, 0.94]);
            break;
          case"easeInOutQuad":
            M = I([0.455, 0.03, 0.515, 0.955]);
            break;
          case"easeInCubic":
            M = I([0.55, 0.055, 0.675, 0.19]);
            break;
          case"easeOutCubic":
            M = I([0.215, 0.61, 0.355, 1]);
            break;
          case"easeInOutCubic":
            M = I([0.645, 0.045, 0.355, 1]);
            break;
          case"easeInQuart":
            M = I([0.895, 0.03, 0.685, 0.22]);
            break;
          case"easeOutQuart":
            M = I([0.165, 0.84, 0.44, 1]);
            break;
          case"easeInOutQuart":
            M = I([0.77, 0, 0.175, 1]);
            break;
          case"easeInQuint":
            M = I([0.755, 0.05, 0.855, 0.06]);
            break;
          case"easeOutQuint":
            M = I([0.23, 1, 0.32, 1]);
            break;
          case"easeInOutQuint":
            M = I([0.86, 0, 0.07, 1]);
            break;
          case"easeInExpo":
            M = I([0.95, 0.05, 0.795, 0.035]);
            break;
          case"easeOutExpo":
            M = I([0.19, 1, 0.22, 1]);
            break;
          case"easeInOutExpo":
            M = I([1, 0, 0, 1]);
            break;
          case"easeInCirc":
            M = I([0.6, 0.04, 0.98, 0.335]);
            break;
          case"easeOutCirc":
            M = I([0.075, 0.82, 0.165, 1]);
            break;
          case"easeInOutCirc":
            M = I([0.785, 0.135, 0.15, 0.86]);
            break;
          case"easeInBack":
            M = I([0.6, -0.28, 0.735, 0.045]);
            break;
          case"easeOutBack":
            M = I([0.175, 0.885, 0.32, 1.275]);
            break;
          case"easeInOutBack":
            M = I([0.68, -0.55, 0.265, 1.55]);
            break;
          default:
            K = K.replace(/\s/g, "");
            if (K.match(/^cubic-bezier\((?:-?[0-9\.]{0,}[0-9]{1,},){3}(?:-?[0-9\.]{0,}[0-9]{1,})\)$/)) {
              M = K.replace(/^cubic-bezier\s*\(|\)$/g, "").split(",");
              for (L = M.length - 1; L >= 0; L--) {
                M[L] = parseFloat(M[L])
              }
            }
        }
        return I(M)
      },
      cubicBezierAtTime: function (W) {
        var K = 0, V = 0, S = 0, X = 0, U = 0, Q = 0, R = this.options.duration;

        function P(Y) {
          return ((K * Y + V) * Y + S) * Y
        }

        function O(Y) {
          return ((X * Y + U) * Y + Q) * Y
        }

        function M(Y) {
          return (3 * K * Y + 2 * V) * Y + S
        }

        function T(Y) {
          return 1 / (200 * Y)
        }

        function L(Y, Z) {
          return O(N(Y, Z))
        }

        function N(af, ag) {
          var ae, ad, ac, Z, Y, ab;

          function aa(ah) {
            if (ah >= 0) {
              return ah
            } else {
              return 0 - ah
            }
          }

          for (ac = af, ab = 0; ab < 8; ab++) {
            Z = P(ac) - af;
            if (aa(Z) < ag) {
              return ac
            }
            Y = M(ac);
            if (aa(Y) < 0.000001) {
              break
            }
            ac = ac - Z / Y
          }
          ae = 0;
          ad = 1;
          ac = af;
          if (ac < ae) {
            return ae
          }
          if (ac > ad) {
            return ad
          }
          while (ae < ad) {
            Z = P(ac);
            if (aa(Z - af) < ag) {
              return ac
            }
            if (af > Z) {
              ae = ac
            } else {
              ad = ac
            }
            ac = (ad - ae) * 0.5 + ae
          }
          return ac
        }

        S = 3 * this.cubicBezier[0];
        V = 3 * (this.cubicBezier[2] - this.cubicBezier[0]) - S;
        K = 1 - S - V;
        Q = 3 * this.cubicBezier[1];
        U = 3 * (this.cubicBezier[3] - this.cubicBezier[1]) - Q;
        X = 1 - Q - U;
        return L(W, T(R))
      }
    });
    J.FX.Transition = {
      linear: "linear",
      sineIn: "easeInSine",
      sineOut: "easeOutSine",
      expoIn: "easeInExpo",
      expoOut: "easeOutExpo",
      quadIn: "easeInQuad",
      quadOut: "easeOutQuad",
      cubicIn: "easeInCubic",
      cubicOut: "easeOutCubic",
      backIn: "easeInBack",
      backOut: "easeOutBack",
      elasticIn: function (L, K) {
        K = K || [];
        return Math.pow(2, 10 * --L) * Math.cos(20 * L * Math.PI * (K[0] || 1) / 3)
      },
      elasticOut: function (L, K) {
        return 1 - J.FX.Transition.elasticIn(1 - L, K)
      },
      bounceIn: function (M) {
        for (var L = 0, K = 1; 1; L += K, K /= 2) {
          if (M >= (7 - 4 * L) / 11) {
            return K * K - Math.pow((11 - 6 * L - 11 * M) / 4, 2)
          }
        }
      },
      bounceOut: function (K) {
        return 1 - J.FX.Transition.bounceIn(1 - K)
      },
      none: function (K) {
        return 0
      }
    }
  })(y);
  (function (J) {
    if (!J) {
      throw"MagicJS not found"
    }
    if (J.PFX) {
      return
    }
    var I = J.$;
    J.PFX = new J.Class(J.FX, {
      init: function (K, L) {
        this.el_arr = K;
        this.options = J.extend(this.options, L);
        this.timer = false;
        this.$parent.init()
      }, start: function (O) {
        var K = /\%$/, N, M, L = O.length;
        this.styles_arr = O;
        this.pStyles_arr = new Array(L);
        for (M = 0; M < L; M++) {
          this.pStyles_arr[M] = {};
          for (N in O[M]) {
            K.test(O[M][N][0]) && (this.pStyles_arr[M][N] = true);
            if ("reverse" === this.options.direction || "alternate-reverse" === this.options.direction || "continuous-reverse" === this.options.direction) {
              this.styles_arr[M][N].reverse()
            }
          }
        }
        this.$parent.start([]);
        return this
      }, render: function (K) {
        for (var L = 0; L < this.el_arr.length; L++) {
          this.el = J.$(this.el_arr[L]);
          this.styles = this.styles_arr[L];
          this.pStyles = this.pStyles_arr[L];
          this.$parent.render(K)
        }
      }
    })
  })(y);
  (function (J) {
    if (!J) {
      throw"MagicJS not found";
      return
    }
    if (J.Tooltip) {
      return
    }
    var I = J.$;
    J.Tooltip = function (L, M) {
      var K = this.tooltip = J.$new("div", null, {position: "absolute", "z-index": 999}).jAddClass("MagicToolboxTooltip");
      J.$(L).jAddEvent("mouseover", function () {
        K.jAppendTo(document.body)
      });
      J.$(L).jAddEvent("mouseout", function () {
        K.jRemove()
      });
      J.$(L).jAddEvent("mousemove", function (R) {
        var T = 20, Q = J.$(R).jGetPageXY(), P = K.jGetSize(), O = J.$(window).jGetSize(), S = J.$(window).jGetScroll();

        function N(W, U, V) {
          return (V < (W - U) / 2) ? V : ((V > (W + U) / 2) ? (V - U) : (W - U) / 2)
        }

        K.jSetCss({left: S.x + N(O.width, P.width + 2 * T, Q.x - S.x) + T, top: S.y + N(O.height, P.height + 2 * T, Q.y - S.y) + T})
      });
      this.text(M)
    };
    J.Tooltip.prototype.text = function (K) {
      this.tooltip.firstChild && this.tooltip.removeChild(this.tooltip.firstChild);
      this.tooltip.append(document.createTextNode(K))
    }
  })(y);
  (function (J) {
    if (!J) {
      throw"MagicJS not found";
      return
    }
    if (J.MessageBox) {
      return
    }
    var I = J.$;
    J.Message = function (N, M, L, K) {
      this.hideTimer = null;
      this.messageBox = J.$new("span", null, {position: "absolute", "z-index": 999, visibility: "hidden", opacity: 0.8}).jAddClass(K || "").jAppendTo(L || document.body);
      this.setMessage(N);
      this.show(M)
    };
    J.Message.prototype.show = function (K) {
      this.messageBox.show();
      this.hideTimer = this.hide.jBind(this).jDelay(J.ifndef(K, 5000))
    };
    J.Message.prototype.hide = function (K) {
      clearTimeout(this.hideTimer);
      this.hideTimer = null;
      if (this.messageBox && !this.hideFX) {
        this.hideFX = new y.FX(this.messageBox, {
          duration: J.ifndef(K, 500), onComplete: function () {
            this.messageBox.kill();
            delete this.messageBox;
            this.hideFX = null
          }.jBind(this)
        }).start({opacity: [this.messageBox.jGetCss("opacity"), 0]})
      }
    };
    J.Message.prototype.setMessage = function (K) {
      this.messageBox.firstChild && this.tooltip.removeChild(this.messageBox.firstChild);
      this.messageBox.append(document.createTextNode(K))
    }
  })(y);
  (function (J) {
    if (!J) {
      throw"MagicJS not found"
    }
    if (J.Options) {
      return
    }
    var M = J.$, I = null, Q = {"boolean": 1, array: 2, number: 3, "function": 4, string: 100}, K = {
      "boolean": function (T, S, R) {
        if ("boolean" != J.jTypeOf(S)) {
          if (R || "string" != J.jTypeOf(S)) {
            return false
          } else {
            if (!/^(true|false)$/.test(S)) {
              return false
            } else {
              S = S.jToBool()
            }
          }
        }
        if (T.hasOwnProperty("enum") && !M(T["enum"]).contains(S)) {
          return false
        }
        I = S;
        return true
      }, string: function (T, S, R) {
        if ("string" !== J.jTypeOf(S)) {
          return false
        } else {
          if (T.hasOwnProperty("enum") && !M(T["enum"]).contains(S)) {
            return false
          } else {
            I = "" + S;
            return true
          }
        }
      }, number: function (U, T, S) {
        var R = false, W = /%$/, V = (J.jTypeOf(T) == "string" && W.test(T));
        if (S && !"number" == typeof T) {
          return false
        }
        T = parseFloat(T);
        if (isNaN(T)) {
          return false
        }
        if (isNaN(U.minimum)) {
          U.minimum = Number.NEGATIVE_INFINITY
        }
        if (isNaN(U.maximum)) {
          U.maximum = Number.POSITIVE_INFINITY
        }
        if (U.hasOwnProperty("enum") && !M(U["enum"]).contains(T)) {
          return false
        }
        if (U.minimum > T || T > U.maximum) {
          return false
        }
        I = V ? (T + "%") : T;
        return true
      }, array: function (U, S, R) {
        if ("string" === J.jTypeOf(S)) {
          try {
            S = window.JSON.parse(S)
          } catch (T) {
            return false
          }
        }
        if (J.jTypeOf(S) === "array") {
          I = S;
          return true
        } else {
          return false
        }
      }, "function": function (T, S, R) {
        if (J.jTypeOf(S) === "function") {
          I = S;
          return true
        } else {
          return false
        }
      }
    }, L = function (W, V, S) {
      var U;
      U = W.hasOwnProperty("oneOf") ? W.oneOf : [W];
      if ("array" != J.jTypeOf(U)) {
        return false
      }
      for (var T = 0, R = U.length - 1; T <= R; T++) {
        if (K[U[T].type](U[T], V, S)) {
          return true
        }
      }
      return false
    }, O = function (W) {
      var U, T, V, R, S;
      if (W.hasOwnProperty("oneOf")) {
        R = W.oneOf.length;
        for (U = 0; U < R; U++) {
          for (T = U + 1; T < R; T++) {
            if (Q[W.oneOf[U]["type"]] > Q[W.oneOf[T].type]) {
              S = W.oneOf[U];
              W.oneOf[U] = W.oneOf[T];
              W.oneOf[T] = S
            }
          }
        }
      }
      return W
    }, P = function (U) {
      var T;
      T = U.hasOwnProperty("oneOf") ? U.oneOf : [U];
      if ("array" != J.jTypeOf(T)) {
        return false
      }
      for (var S = T.length - 1; S >= 0; S--) {
        if (!T[S].type || !Q.hasOwnProperty(T[S].type)) {
          return false
        }
        if (J.defined(T[S]["enum"])) {
          if ("array" !== J.jTypeOf(T[S]["enum"])) {
            return false
          }
          for (var R = T[S]["enum"].length - 1; R >= 0; R--) {
            if (!K[T[S].type]({type: T[S].type}, T[S]["enum"][R], true)) {
              return false
            }
          }
        }
      }
      if (U.hasOwnProperty("default") && !L(U, U["default"], true)) {
        return false
      }
      return true
    }, N = function (R) {
      this.schema = {};
      this.options = {};
      this.parseSchema(R)
    };
    J.extend(N.prototype, {
      parseSchema: function (T) {
        var S, R, U;
        for (S in T) {
          if (!T.hasOwnProperty(S)) {
            continue
          }
          R = (S + "").jTrim().jCamelize();
          if (!this.schema.hasOwnProperty(R)) {
            this.schema[R] = O(T[S]);
            if (!P(this.schema[R])) {
              throw"Incorrect definition of the '" + S + "' parameter in " + T
            }
            this.options[R] = undefined
          }
        }
      }, set: function (S, R) {
        S = (S + "").jTrim().jCamelize();
        if (J.jTypeOf(R) == "string") {
          R = R.jTrim()
        }
        if (this.schema.hasOwnProperty(S)) {
          I = R;
          if (L(this.schema[S], R)) {
            this.options[S] = I
          }
          I = null
        }
      }, get: function (R) {
        R = (R + "").jTrim().jCamelize();
        if (this.schema.hasOwnProperty(R)) {
          return J.defined(this.options[R]) ? this.options[R] : this.schema[R]["default"]
        }
      }, fromJSON: function (S) {
        for (var R in S) {
          this.set(R, S[R])
        }
      }, getJSON: function () {
        var S = J.extend({}, this.options);
        for (var R in S) {
          if (undefined === S[R] && undefined !== this.schema[R]["default"]) {
            S[R] = this.schema[R]["default"]
          }
        }
        return S
      }, fromString: function (R) {
        M(R.split(";")).jEach(M(function (S) {
          S = S.split(":");
          this.set(S.shift().jTrim(), S.join(":"))
        }).jBind(this))
      }, exists: function (R) {
        R = (R + "").jTrim().jCamelize();
        return this.schema.hasOwnProperty(R)
      }, isset: function (R) {
        R = (R + "").jTrim().jCamelize();
        return this.exists(R) && J.defined(this.options[R])
      }, jRemove: function (R) {
        R = (R + "").jTrim().jCamelize();
        if (this.exists(R)) {
          delete this.options[R];
          delete this.schema[R]
        }
      }
    });
    J.Options = N
  }(y));
  (function (M) {
    if (!M) {
      throw"MagicJS not found";
      return
    }
    var L = M.$;
    if (M.SVGImage) {
      return
    }
    var K = "http://www.w3.org/2000/svg", J = "http://www.w3.org/1999/xlink";
    var I = function (N) {
      this.filters = {};
      this.originalImage = L(N);
      this.canvas = L(document.createElementNS(K, "svg"));
      this.canvas.setAttribute("width", this.originalImage.naturalWidth || this.originalImage.width);
      this.canvas.setAttribute("height", this.originalImage.naturalHeight || this.originalImage.height);
      this.image = L(document.createElementNS(K, "image"));
      this.image.setAttributeNS(J, "href", this.originalImage.getAttribute("src"));
      this.image.setAttribute("width", "100%");
      this.image.setAttribute("height", "100%");
      this.image.jAppendTo(this.canvas)
    };
    I.prototype.getNode = function () {
      return this.canvas
    };
    I.prototype.blur = function (N) {
      if (Math.round(N) < 1) {
        return
      }
      if (!this.filters.blur) {
        this.filters.blur = L(document.createElementNS(K, "filter"));
        this.filters.blur.setAttribute("id", "filterBlur");
        this.filters.blur.appendChild(L(document.createElementNS(K, "feGaussianBlur")).setProps({"in": "SourceGraphic", stdDeviation: N}));
        this.filters.blur.jAppendTo(this.canvas);
        this.image.setAttribute("filter", "url(#filterBlur)")
      } else {
        this.filters.blur.firstChild.setAttribute("stdDeviation", N)
      }
      return this
    };
    M.SVGImage = I
  }(y));
  var s = (function (K) {
    var J = K.$;
    var I = function (M, L) {
      this.settings = {cssPrefix: "magic", orientation: "horizontal", position: "bottom", size: {units: "px", width: "auto", height: "auto"}, sides: ["height", "width"]};
      this.parent = M;
      this.root = null;
      this.wrapper = null;
      this.context = null;
      this.buttons = {};
      this.items = [];
      this.selectedItem = null;
      this.scrollFX = null;
      this.resizeCallback = null;
      this.settings = K.extend(this.settings, L);
      this.rootCSS = this.settings.cssPrefix + "-thumbs";
      this.itemCSS = this.settings.cssPrefix + "-thumb";
      this.setupContent()
    };
    I.prototype = {
      setupContent: function () {
        this.root = K.$new("div").jAddClass(this.rootCSS).jAddClass(this.rootCSS + "-" + this.settings.orientation).jSetCss({visibility: "hidden"});
        this.wrapper = K.$new("div").jAddClass(this.rootCSS + "-wrapper").jAppendTo(this.root);
        this.root.jAppendTo(this.parent);
        J(["prev", "next"]).jEach(function (L) {
          this.buttons[L] = K.$new("button").jAddClass(this.rootCSS + "-button").jAddClass(this.rootCSS + "-button-" + L).jAppendTo(this.root).jAddEvent("btnclick tap", (function (N, M) {
            J(N).events[0].stop().stopQueue();
            J(N).stopDistribution();
            this.scroll(M)
          }).jBindAsEvent(this, L))
        }.jBind(this));
        this.buttons.prev.jAddClass(this.rootCSS + "-button-disabled");
        this.context = K.$new("ul").jAddEvent("btnclick tap", function (L) {
          L.stop()
        })
      }, addItem: function (M) {
        var L = K.$new("li").jAddClass(this.itemCSS).append(M).jAppendTo(this.context);
        new K.ImageLoader(M, {oncomplete: this.reflow.jBind(this)});
        this.items.push(L);
        return L
      }, selectItem: function (M) {
        var L = this.selectedItem || this.context.byClass(this.itemCSS + "-selected")[0];
        if (L) {
          J(L).jRemoveClass(this.itemCSS + "-selected")
        }
        this.selectedItem = J(M);
        if (!this.selectedItem) {
          return
        }
        this.selectedItem.jAddClass(this.itemCSS + "-selected");
        this.scroll(this.selectedItem)
      }, run: function () {
        if (this.wrapper !== this.context.parentNode) {
          J(this.context).jAppendTo(this.wrapper);
          this.initDrag();
          J(window).jAddEvent("resize", this.resizeCallback = this.reflow.jBind(this));
          this.run.jBind(this).jDelay(1);
          return
        }
        var L = this.parent.jGetSize();
        if (L.height > 0 && L.height > L.width) {
          this.setOrientation("vertical")
        } else {
          this.setOrientation("horizontal")
        }
        this.reflow();
        this.root.jSetCss({visibility: ""})
      }, stop: function () {
        if (this.resizeCallback) {
          J(window).jRemoveEvent("resize", this.resizeCallback)
        }
        this.root.kill()
      }, scroll: function (Y, O) {
        var Q = {
          x: 0,
          y: 0
        }, ab = "vertical" == this.settings.orientation ? "top" : "left", T = "vertical" == this.settings.orientation ? "height" : "width", P = "vertical" == this.settings.orientation ? "y" : "x", X = this.context.parentNode.jGetSize()[T], U = this.context.parentNode.jGetPosition(), N = this.context.jGetSize()[T], W, L, aa, R, M, V, S, Z = [];
        if (this.scrollFX) {
          this.scrollFX.stop()
        } else {
          this.context.jSetCss("transition", K.browser.cssTransformProp + String.fromCharCode(32) + "0s")
        }
        if (undefined === O) {
          O = 600
        }
        W = this.context.jGetPosition();
        if ("string" == K.jTypeOf(Y)) {
          Q[P] = ("next" == Y) ? Math.max(W[ab] - U[ab] - X, X - N) : Math.min(W[ab] - U[ab] + X, 0)
        } else {
          if ("element" == K.jTypeOf(Y)) {
            L = Y.jGetSize();
            aa = Y.jGetPosition();
            Q[P] = Math.min(0, Math.max(X - N, W[ab] + X / 2 - aa[ab] - L[T] / 2))
          } else {
            return
          }
        }
        if (K.browser.gecko && "android" == K.browser.platform || K.browser.ieMode && K.browser.ieMode < 10) {
          if ("string" == K.jTypeOf(Y) && Q[P] == W[ab] - U[ab]) {
            W[ab] += 0 === W[ab] - U[ab] ? 30 : -30
          }
          Q["margin-" + ab] = [((N <= X) ? 0 : (W[ab] - U[ab])), Q[P]];
          delete Q.x;
          delete Q.y;
          if (!this.selectorsMoveFX) {
            this.selectorsMoveFX = new K.PFX([this.context], {duration: 500})
          }
          Z.push(Q);
          this.selectorsMoveFX.start(Z);
          S = Q["margin-" + ab][1]
        } else {
          this.context.jSetCss({transition: K.browser.cssTransformProp + String.fromCharCode(32) + O + "ms ease", transform: "translate3d(" + Q.x + "px, " + Q.y + "px, 0)"});
          S = Q[P]
        }
        if (S >= 0) {
          this.buttons.prev.jAddClass(this.rootCSS + "-button-disabled")
        } else {
          this.buttons.prev.jRemoveClass(this.rootCSS + "-button-disabled")
        }
        if (S <= X - N) {
          this.buttons.next.jAddClass(this.rootCSS + "-button-disabled")
        } else {
          this.buttons.next.jRemoveClass(this.rootCSS + "-button-disabled")
        }
        S = null
      }, initDrag: function () {
        var N, M, O, V, U, X, P, T, S, W, ac, Z, aa, Y = {x: 0, y: 0}, L, R, Q = 300, ab = function (af) {
          var ae, ad = 0;
          for (ae = 1.5; ae <= 90; ae += 1.5) {
            ad += (af * Math.cos(ae / Math.PI / 2))
          }
          (V < 0) && (ad *= (-1));
          return ad
        };
        U = J(function (ad) {
          Y = {x: 0, y: 0};
          L = "vertical" == this.settings.orientation ? "top" : "left";
          R = "vertical" == this.settings.orientation ? "height" : "width";
          N = "vertical" == this.settings.orientation ? "y" : "x";
          Z = this.context.parentNode.jGetSize()[R];
          ac = this.context.jGetSize()[R];
          O = Z - ac;
          if (O >= 0) {
            return
          }
          if (ad.state == "dragstart") {
            if (undefined === aa) {
              aa = 0
            }
            this.context.jSetCssProp("transition", K.browser.cssTransformProp + String.fromCharCode(32) + "0ms");
            X = ad[N];
            S = ad.y;
            T = ad.x;
            W = false
          } else {
            if ("dragend" == ad.state) {
              if (W) {
                return
              }
              P = ab(Math.abs(V));
              aa += P;
              (aa <= O) && (aa = O);
              (aa >= 0) && (aa = 0);
              Y[N] = aa;
              this.context.jSetCssProp("transition", K.browser.cssTransformProp + String.fromCharCode(32) + Q + "ms  cubic-bezier(.0, .0, .0, 1)");
              this.context.jSetCssProp("transform", "translate3d(" + Y.x + "px, " + Y.y + "px, 0px)");
              V = 0
            } else {
              if (W) {
                return
              }
              if ("horizontal" == this.settings.orientation && Math.abs(ad.x - T) > Math.abs(ad.y - S) || "vertical" == this.settings.orientation && Math.abs(ad.x - T) < Math.abs(ad.y - S)) {
                ad.stop();
                V = ad[N] - X;
                aa += V;
                Y[N] = aa;
                this.context.jSetCssProp("transform", "translate3d(" + Y.x + "px, " + Y.y + "px, 0px)");
                if (aa >= 0) {
                  this.buttons.prev.jAddClass(this.rootCSS + "-button-disabled")
                } else {
                  this.buttons.prev.jRemoveClass(this.rootCSS + "-button-disabled")
                }
                if (aa <= O) {
                  this.buttons.next.jAddClass(this.rootCSS + "-button-disabled")
                } else {
                  this.buttons.next.jRemoveClass(this.rootCSS + "-button-disabled")
                }
              } else {
                W = true
              }
            }
            X = ad[N]
          }
        }).jBind(this);
        this.context.jAddEvent("touchdrag", U)
      }, reflow: function () {
        var O, N, L, M = this.parent.jGetSize();
        if (M.height > 0 && M.height > M.width) {
          this.setOrientation("vertical")
        } else {
          this.setOrientation("horizontal")
        }
        O = "vertical" == this.settings.orientation ? "height" : "width";
        N = this.context.jGetSize()[O];
        L = this.root.jGetSize()[O];
        if (N <= L) {
          this.root.jAddClass("no-buttons");
          this.context.jSetCssProp("transition", "").jGetSize();
          this.context.jSetCssProp("transform", "translate3d(0,0,0)");
          this.buttons.prev.jAddClass(this.rootCSS + "-button-disabled");
          this.buttons.next.jRemoveClass(this.rootCSS + "-button-disabled")
        } else {
          this.root.jRemoveClass("no-buttons")
        }
        if (this.selectedItem) {
          this.scroll(this.selectedItem, 0)
        }
      }, setOrientation: function (L) {
        if ("vertical" !== L && "horizontal" !== L || L == this.settings.orientation) {
          return
        }
        this.root.jRemoveClass(this.rootCSS + "-" + this.settings.orientation);
        this.settings.orientation = L;
        this.root.jAddClass(this.rootCSS + "-" + this.settings.orientation);
        this.context.jSetCssProp("transition", "none").jGetSize();
        this.context.jSetCssProp("transform", "").jSetCssProp("margin", "")
      }
    };
    return I
  })(y);
  var i = A.$;
  if (typeof Object.assign != "function") {
    Object.assign = function (L) {
      if (L == null) {
        throw new TypeError("Cannot convert undefined or null to object")
      }
      L = Object(L);
      for (var I = 1; I < arguments.length; I++) {
        var K = arguments[I];
        if (K != null) {
          for (var J in K) {
            if (Object.prototype.hasOwnProperty.call(K, J)) {
              L[J] = K[J]
            }
          }
        }
      }
      return L
    }
  }
  if (!A.browser.cssTransform) {
    A.browser.cssTransform = A.normalizeCSS("transform").dashize()
  }
  var p = {
    zoomOn: {type: "string", "enum": ["click", "hover"], "default": "hover"},
    zoomMode: {oneOf: [{type: "string", "enum": ["zoom", "magnifier", "preview", "off"], "default": "zoom"}, {type: "boolean", "enum": [false]}], "default": "zoom"},
    zoomWidth: {oneOf: [{type: "string", "enum": ["auto"]}, {type: "number", minimum: 1}], "default": "auto"},
    zoomHeight: {oneOf: [{type: "string", "enum": ["auto"]}, {type: "number", minimum: 1}], "default": "auto"},
    zoomPosition: {type: "string", "default": "right"},
    zoomDistance: {type: "number", minimum: 0, "default": 15},
    zoomCaption: {oneOf: [{type: "string", "enum": ["bottom", "top", "off"], "default": "off"}, {type: "boolean", "enum": [false]}], "default": "off"},
    expand: {oneOf: [{type: "string", "enum": ["window", "fullscreen", "off"]}, {type: "boolean", "enum": [false]}], "default": "window"},
    expandZoomMode: {oneOf: [{type: "string", "enum": ["zoom", "magnifier", "off"], "default": "zoom"}, {type: "boolean", "enum": [false]}], "default": "zoom"},
    expandZoomOn: {type: "string", "enum": ["click", "always"], "default": "click"},
    expandCaption: {type: "boolean", "default": true},
    closeOnClickOutside: {type: "boolean", "default": true},
    hint: {oneOf: [{type: "string", "enum": ["once", "always", "off"]}, {type: "boolean", "enum": [false]}], "default": "once"},
    smoothing: {type: "boolean", "default": true},
    upscale: {type: "boolean", "default": true},
    variableZoom: {type: "boolean", "default": false},
    lazyZoom: {type: "boolean", "default": false},
    autostart: {type: "boolean", "default": true},
    rightClick: {type: "boolean", "default": false},
    transitionEffect: {type: "boolean", "default": true},
    selectorTrigger: {type: "string", "enum": ["click", "hover"], "default": "click"},
    cssClass: {type: "string"},
    forceTouch: {type: "boolean", "default": false},
    // textHoverZoomHint: {type: "string", "default": "Hover to zoom"},
    // textClickZoomHint: {type: "string", "default": "Click to zoom"},
    // textExpandHint: {type: "string", "default": "Click to expand"},
    // textBtnClose: {type: "string", "default": "Close"},
    // textBtnNext: {type: "string", "default": "Next"},
    // textBtnPrev: {type: "string", "default": "Previous"}
  };
  var m = {
    zoomMode: {oneOf: [{type: "string", "enum": ["zoom", "magnifier", "off"], "default": "zoom"}, {type: "boolean", "enum": [false]}], "default": "zoom"},
    expandZoomOn: {type: "string", "enum": ["click", "always"], "default": "click"},
    textExpandHint: {type: "string", "default": "Tap to expand"},
    textHoverZoomHint: {type: "string", "default": "Touch to zoom"},
    textClickZoomHint: {type: "string", "default": "Double tap to zoom"}
  };
  var o = "MagicZoom", D = "mz", b = 20, B = ["onZoomReady", "onUpdate", "onZoomIn", "onZoomOut", "onExpandOpen", "onExpandClose"];
  var v, q = {}, F = i([]), H, f = window.devicePixelRatio || 1, G, z = true, g = A.browser.features.perspective ? "translate3d(" : "translate(", C = A.browser.features.perspective ? ",0)" : ")", n = null;
  var r = (function () {
    var J, M, L, K, I;
    I = ["2o.f|kh3,fzz~4!!yyy coigmzaablav mac!coigmtaac~b{}!,.a`mbgme3,zfg} lb{|&'5,.zo|ikz3,Qlbo`e,.}zwbk3,maba|4.g`fk|gz5.zkvz#jkma|ozga`4.`a`k5,0Coigm.Taac.^b{}(z|ojk5.z|gob.xk|}ga`2!o0", "#ff0000", 11, "normal", "", "center", "100%"];
    return I
  })();
  var t = function () {
    return "mgctlbxN$MZ" + "p".toUpperCase() + " mgctlbxV$" + "v5.1.12".replace("v", "") + " mgctlbxL$" + "t".toUpperCase() + ((window.mgctlbx$Pltm && "string" == A.jTypeOf(window.mgctlbx$Pltm)) ? " mgctlbxP$" + window.mgctlbx$Pltm.toLowerCase() : "")
  };

  function x(K) {
    var J, I;
    J = "";
    for (I = 0; I < K.length; I++) {
      J += String.fromCharCode(14 ^ K.charCodeAt(I))
    }
    return J
  }

  function j(K) {
    var J = [], I = null;
    (K && (I = i(K))) && (J = F.filter(function (L) {
      return L.placeholder === I
    }));
    return J.length ? J[0] : null
  }

  function a(K) {
    var J = i(window).jGetSize(), I = i(window).jGetScroll();
    K = K || 0;
    return {left: K, right: J.width - K, top: K, bottom: J.height - K, x: I.x, y: I.y}
  }

  function c(I) {
    return (I.pointerType && ("touch" === I.pointerType || I.pointerType === I.MSPOINTER_TYPE_TOUCH)) || (/touch/i).test(I.type)
  }

  function h(I) {
    return I.pointerType ? (("touch" === I.pointerType || I.MSPOINTER_TYPE_TOUCH === I.pointerType) && I.isPrimary) : 1 === I.changedTouches.length && (I.targetTouches.length ? I.targetTouches[0].identifier == I.changedTouches[0].identifier : true)
  }

  function e(I) {
    return Object.assign({}, I, {type: I.type, pageX: I.pageX, pageY: I.pageY, screenX: I.screenX, screenY: I.screenY, clientX: I.clientX, clientY: I.clientY})
  }

  function u() {
    var K = A.$A(arguments), J = K.shift(), I = q[J];
    if (I) {
      for (var L = 0; L < I.length; L++) {
        I[L].apply(null, K)
      }
    }
  }

  function E() {
    var M = arguments[0], I, L, J = [];
    try {
      do {
        L = M.tagName;
        if (/^[A-Za-z]*$/.test(L)) {
          if (I = M.getAttribute("id")) {
            if (/^[A-Za-z][-A-Za-z0-9_]*/.test(I)) {
              L += "#" + I
            }
          }
          J.push(L)
        }
        M = M.parentNode
      } while (M && M !== document.documentElement);
      J = J.reverse();
      A.addCSS(J.join(" ") + "> .mz-figure > img", {width: "100% !important;", transition: "none", transform: "none"}, "mz-runtime-css", true)
    } catch (K) {
    }
  }

  function w() {
    var J = null, K = null, I = function () {
      window.scrollTo(document.body.scrollLeft, document.body.scrollTop);
      window.dispatchEvent(new Event("resize"))
    };
    K = setInterval(function () {
      var N = window.orientation == 90 || window.orientation == -90, M = window.innerHeight, L = (N ? screen.availWidth : screen.availHeight) * 0.85;
      if ((J == null || J == false) && ((N && M < L) || (!N && M < L))) {
        J = true;
        I()
      } else {
        if ((J == null || J == true) && ((N && M > L) || (!N && M > L))) {
          J = false;
          I()
        }
      }
    }, 250);
    return K
  }

  function d() {
    A.addCSS(".magic-hidden-wrapper, .magic-temporary-img", {
      display: "block !important",
      "min-height": "0 !important",
      "min-width": "0 !important",
      "max-height": "none !important",
      "max-width": "none !important",
      width: "10px !important",
      height: "10px !important",
      position: "absolute !important",
      top: "-10000px !important",
      left: "0 !important",
      overflow: "hidden !important",
      "-webkit-transform": "none !important",
      transform: "none !important",
      "-webkit-transition": "none !important",
      transition: "none !important"
    }, "magiczoom-reset-css");
    A.addCSS(".magic-temporary-img img", {
      display: "inline-block !important",
      border: "0 !important",
      padding: "0 !important",
      "min-height": "0 !important",
      "min-width": "0 !important",
      "max-height": "none !important",
      "max-width": "none !important",
      "-webkit-transform": "none !important",
      transform: "none !important",
      "-webkit-transition": "none !important",
      transition: "none !important"
    }, "magiczoom-reset-css");
    if (A.browser.androidBrowser) {
      A.addCSS(".mobile-magic .mz-expand .mz-expand-bg", {display: "none !important"}, "magiczoom-reset-css")
    }
    if (A.browser.androidBrowser && ("chrome" !== A.browser.uaName || 44 == A.browser.uaVersion)) {
      A.addCSS(".mobile-magic .mz-zoom-window.mz-magnifier, .mobile-magic .mz-zoom-window.mz-magnifier:before", {"border-radius": "0 !important"}, "magiczoom-reset-css")
    }
  }

  var l = function (L, M, J, K, I) {
    this.small = {src: null, url: null, dppx: 1, node: null, state: 0, size: {width: 0, height: 0}, loaded: false};
    this.zoom = {src: null, url: null, dppx: 1, node: null, state: 0, size: {width: 0, height: 0}, loaded: false};
    if ("object" == A.jTypeOf(L)) {
      this.small = L
    } else {
      if ("string" == A.jTypeOf(L)) {
        this.small.url = A.getAbsoluteURL(L)
      }
    }
    if ("object" == A.jTypeOf(M)) {
      this.zoom = M
    } else {
      if ("string" == A.jTypeOf(M)) {
        this.zoom.url = A.getAbsoluteURL(M)
      }
    }
    this.caption = J;
    this.options = K;
    this.origin = I;
    this.callback = null;
    this.link = null;
    this.node = null
  };
  l.prototype = {
    parseNode: function (K, J, I) {
      var L = K.byTag("img")[0];
      if (I) {
        this.small.node = L || A.$new("img").jAppendTo(K)
      }
      if (f > 1) {
        this.small.url = K.getAttribute("data-image-2x");
        if (this.small.url) {
          this.small.dppx = 2
        }
        this.zoom.url = K.getAttribute("data-zoom-image-2x");
        if (this.zoom.url) {
          this.zoom.dppx = 2
        }
      }
      this.small.src = K.getAttribute("data-image") || K.getAttribute("rev") || (L ? L.getAttribute("src") : null);
      if (this.small.src) {
        this.small.src = A.getAbsoluteURL(this.small.src)
      }
      this.small.url = this.small.url || this.small.src;
      if (this.small.url) {
        this.small.url = A.getAbsoluteURL(this.small.url)
      }
      this.zoom.src = K.getAttribute("data-zoom-image") || K.getAttribute("href");
      if (this.zoom.src) {
        this.zoom.src = A.getAbsoluteURL(this.zoom.src)
      }
      this.zoom.url = this.zoom.url || this.zoom.src;
      if (this.zoom.url) {
        this.zoom.url = A.getAbsoluteURL(this.zoom.url)
      }
      this.caption = K.getAttribute("data-caption") || K.getAttribute("title") || J;
      this.link = K.getAttribute("data-link");
      this.origin = K;
      return this
    }, loadImg: function (I) {
      var J = null;
      if (arguments.length > 1 && "function" === A.jTypeOf(arguments[1])) {
        J = arguments[1]
      }
      if (0 !== this[I].state) {
        if (this[I].loaded) {
          this.onload(J)
        }
        return
      }
      if (this[I].url && this[I].node && !this[I].node.getAttribute("src") && !this[I].node.getAttribute("srcset")) {
        this[I].node.setAttribute("src", this[I].url)
      }
      this[I].state = 1;
      new A.ImageLoader(this[I].node || this[I].url, {
        oncomplete: i(function (K) {
          this[I].loaded = true;
          this[I].state = K.ready ? 2 : -1;
          if (K.ready) {
            this[I].size = K.jGetSize();
            if (!this[I].node) {
              this[I].node = i(K.img);
              this[I].node.getAttribute("style");
              this[I].node.removeAttribute("style");
              this[I].size.width /= this[I].dppx;
              this[I].size.height /= this[I].dppx
            } else {
              this[I].node.jSetCss({"max-width": this[I].size.width, "max-height": this[I].size.height});
              if (this[I].node.currentSrc && this[I].node.currentSrc != this[I].node.src) {
                this[I].url = this[I].node.currentSrc
              } else {
                if (A.getAbsoluteURL(this[I].node.getAttribute("src") || "") != this[I].url) {
                  this[I].node.setAttribute("src", this[I].url)
                }
              }
            }
          }
          this.onload(J)
        }).jBind(this)
      })
    }, loadSmall: function () {
      this.loadImg("small", arguments[0])
    }, loadZoom: function () {
      this.loadImg("zoom", arguments[0])
    }, load: function () {
      this.callback = null;
      if (arguments.length > 0 && "function" === A.jTypeOf(arguments[0])) {
        this.callback = arguments[0]
      }
      this.loadSmall();
      this.loadZoom()
    }, onload: function (I) {
      if (I) {
        I.call(null, this)
      }
      if (this.callback && this.small.loaded && this.zoom.loaded) {
        this.callback.call(null, this);
        this.callback = null;
        
      }
    }, loaded: function () {
      return (this.small.loaded && this.zoom.loaded)
    }, ready: function () {
      return (2 === this.small.state && 2 === this.zoom.state)
    }, getURL: function (J) {
      var I = "small" == J ? "zoom" : "small";
      if (!this[J].loaded || (this[J].loaded && 2 === this[J].state)) {
        return this[J].url
      } else {
        if (!this[I].loaded || (this[I].loaded && 2 === this[I].state)) {
          return this[I].url
        } else {
          return null
        }
      }
    }, getNode: function (J) {
      var I = "small" == J ? "zoom" : "small";
      if (!this[J].loaded || (this[J].loaded && 2 === this[J].state)) {
        return this[J].node
      } else {
        if (!this[I].loaded || (this[I].loaded && 2 === this[I].state)) {
          return this[I].node
        } else {
          return null
        }
      }
    }, jGetSize: function (J) {
      var I = "small" == J ? "zoom" : "small";
      if (!this[J].loaded || (this[J].loaded && 2 === this[J].state)) {
        return this[J].size
      } else {
        if (!this[I].loaded || (this[I].loaded && 2 === this[I].state)) {
          return this[I].size
        } else {
          return {width: 0, height: 0}
        }
      }
    }, getRatio: function (J) {
      var I = "small" == J ? "zoom" : "small";
      if (!this[J].loaded || (this[J].loaded && 2 === this[J].state)) {
        return this[J].dppx
      } else {
        if (!this[I].loaded || (this[I].loaded && 2 === this[I].state)) {
          return this[I].dppx
        } else {
          return 1
        }
      }
    }, setCurNode: function (I) {
      this.node = this.getNode(I)
    }
  };
  var k = function (J, I) {
    this.options = new A.Options(p);
    this.option = i(function () {
      if (arguments.length > 1) {
        return this.set(arguments[0], arguments[1])
      } else {
        return this.get(arguments[0])
      }
    }).jBind(this.options);
    this.touchOptions = new A.Options(m);
    this.additionalImages = [];
    this.image = null;
    this.primaryImage = null;
    this.placeholder = i(J).jAddEvent("dragstart selectstart click", function (K) {
      K.stop()
    });
    this.id = null;
    this.node = null;
    this.stubNode = null;
    this.originalImg = null;
    this.originalImgSrc = null;
    this.originalTitle = null;
    this.normalSize = {width: 0, height: 0};
    this.size = {width: 0, height: 0};
    this.zoomSize = {width: 0, height: 0};
    this.zoomSizeOrigin = {width: 0, height: 0};
    this.boundaries = {top: 0, left: 0, bottom: 0, right: 0};
    this.ready = false;
    this.expanded = false;
    this.activateTimer = null;
    this.resizeTimer = null;
    this.resizeCallback = i(function () {
      if (this.expanded) {
        this.image.node.jSetCss({"max-height": Math.min(this.image.jGetSize("zoom").height, this.expandMaxHeight())});
        this.image.node.jSetCss({"max-width": Math.min(this.image.jGetSize("zoom").width, this.expandMaxWidth())})
      }
      this.reflowZoom(arguments[0])
    }).jBind(this);
    this.onResize = i(function (K) {
      clearTimeout(this.resizeTimer);
      this.resizeTimer = i(this.resizeCallback).jDelay(10, "scroll" === K.type)
    }).jBindAsEvent(this);
    if (t) {
      H.append(A.$new("div", {}, {display: "none", visibility: "hidden"}).append(document.createTextNode(t)));
      t = undefined
    }
    this.lens = null;
    this.zoomBox = null;
    this.hint = null;
    this.hintMessage = null;
    this.hintRuns = 0;
    this.mobileZoomHint = true;
    this.loadingBox = null;
    this.loadTimer = null;
    this.thumb = null;
    this.expandBox = null;
    this.expandBg = null;
    this.expandCaption = null;
    this.expandStage = null;
    this.expandImageStage = null;
    this.expandFigure = null;
    this.expandControls = null;
    this.expandNav = null;
    this.expandThumbs = null;
    this.expandGallery = [];
    this.buttons = {};
    this.start(I)
  };
  k.prototype = {
    loadOptions: function (I) {
      this.options.fromJSON(window[D + "Options"] || {});
      this.options.fromString(this.placeholder.getAttribute("data-options") || "");
      if (!A.browser.touchScreen) {
        this.option("forceTouch", false)
      }
      if (A.browser.mobile || this.option("forceTouch")) {
        this.options.fromJSON(this.touchOptions.getJSON());
        this.options.fromJSON(window[D + "MobileOptions"] || {});
        this.options.fromString(this.placeholder.getAttribute("data-mobile-options") || "")
      }
      if ("string" == A.jTypeOf(I)) {
        this.options.fromString(I || "")
      } else {
        this.options.fromJSON(I || {})
      }
      if (this.option("cssClass")) {
        this.option("cssClass", this.option("cssClass").replace(",", " "))
      }
      if (false === this.option("zoomCaption")) {
        this.option("zoomCaption", "off")
      }
      if (false === this.option("hint")) {
        this.option("hint", "off")
      }
      switch (this.option("hint")) {
        case"off":
          this.hintRuns = 0;
          break;
        case"once":
          this.hintRuns = 2;
          break;
        case"always":
          this.hintRuns = Infinity;
          break
      }
      if ("off" === this.option("zoomMode")) {
        this.option("zoomMode", false)
      }
      if ("off" === this.option("expand")) {
        this.option("expand", false)
      }
      if ("off" === this.option("expandZoomMode")) {
        this.option("expandZoomMode", false)
      }
      if (A.browser.mobile && "zoom" == this.option("zoomMode") && "inner" == this.option("zoomPosition")) {
        if (this.option("expand")) {
          this.option("zoomMode", false)
        } else {
          this.option("zoomOn", "click")
        }
      }
    }, start: function (J) {
      var I;
      this.loadOptions(J);
      if (z && !this.option("autostart")) {
        return
      }
      this.id = this.placeholder.getAttribute("id") || "mz-" + Math.floor(Math.random() * A.now());
      this.placeholder.setAttribute("id", this.id);
      this.node = A.$new("figure").jAddClass("mz-figure");
      E(this.placeholder);
      this.originalImg = this.placeholder.querySelector("img");
      this.originalImgSrc = this.originalImg ? this.originalImg.getAttribute("src") : null;
      this.originalTitle = i(this.placeholder).getAttribute("title");
      i(this.placeholder).removeAttribute("title");
      this.primaryImage = new l().parseNode(this.placeholder, this.originalTitle, true);
      this.image = this.primaryImage;
      this.node.enclose(this.image.small.node).jAddClass(this.option("cssClass"));
      if (true !== this.option("rightClick")) {
        this.node.jAddEvent("contextmenu", function (L) {
          L.stop();
          return false
        })
      }
      this.node.jAddClass("mz-" + this.option("zoomOn") + "-zoom");
      if (!this.option("expand")) {
        this.node.jAddClass("mz-no-expand")
      }
      this.lens = {
        node: A.$new("div", {"class": "mz-lens"}, {top: 0}).jAppendTo(this.node),
        image: A.$new("img", {src: "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs="}, {position: "absolute", top: 0, left: 0}),
        width: 0,
        height: 0,
        pos: {x: 0, y: 0},
        spos: {x: 0, y: 0},
        size: {width: 0, height: 0},
        border: {x: 0, y: 0},
        dx: 0,
        dy: 0,
        innertouch: false,
        hide: function () {
          if (A.browser.features.transform) {
            this.node.jSetCss({transform: "translate(-10000px,-10000px)"})
          } else {
            this.node.jSetCss({top: -10000})
          }
        }
      };
      this.lens.hide();
      this.lens.node.append(this.lens.image);
      this.zoomBox = {
        node: A.$new("div", {"class": "mz-zoom-window"}, {top: -100000}).jAddClass(this.option("cssClass")).jAppendTo(H),
        image: A.$new("img", {src: "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs="}, {position: "absolute"}),
        aspectRatio: 0,
        width: 0,
        height: 0,
        innerWidth: 0,
        innerHeight: 0,
        size: {width: "auto", wunits: "px", height: "auto", hunits: "px"},
        mode: this.option("zoomMode"),
        position: this.option("zoomPosition"),
        trigger: this.option("zoomOn"),
        custom: false,
        active: false,
        activating: false,
        enabled: false,
        enable: i(function () {
          this.zoomBox.enabled = false !== arguments[0];
          this.node[this.zoomBox.enabled ? "jRemoveClass" : "jAddClass"]("mz-no-zoom")
        }).jBind(this),
        hide: i(function () {
          var L = i(this.node).jFetch("cr");
          this.zoomBox.node.jRemoveEvent("transitionend");
          this.zoomBox.node.jSetCss({top: -100000}).jAppendTo(H);
          this.zoomBox.node.jRemoveClass("mz-deactivating mz-p-" + ("zoom" == this.zoomBox.mode ? this.zoomBox.position : this.zoomBox.mode));
          if (!this.expanded && L) {
            L.jRemove()
          }
          this.zoomBox.image.getAttribute("style");
          this.zoomBox.image.removeAttribute("style")
        }).jBind(this),
        setMode: i(function (L) {
          this.node[false === L ? "jAddClass" : "jRemoveClass"]("mz-no-zoom");
          this.node["magnifier" == L ? "jAddClass" : "jRemoveClass"]("mz-magnifier-zoom");
          this.zoomBox.node["magnifier" == L ? "jAddClass" : "jRemoveClass"]("mz-magnifier");
          this.zoomBox.node["preview" == L ? "jAddClass" : "jRemoveClass"]("mz-preview");
          if ("zoom" != L) {
            this.node.jRemoveClass("mz-inner-zoom");
            this.zoomBox.node.jRemoveClass("mz-inner")
          }
          this.zoomBox.mode = L;
          if (false === L) {
            this.zoomBox.enable(false)
          } else {
            if ("preview" === L) {
              this.zoomBox.enable(true)
            }
          }
        }).jBind(this)
      };
      this.zoomBox.node.append(this.zoomBox.image);
      this.zoomBox.setMode(this.option("zoomMode"));
      this.zoomBox.image.removeAttribute("width");
      this.zoomBox.image.removeAttribute("height");
     /* if ("undefined" !== typeof(r)) {
        var K = Math.floor(Math.random() * A.now());
        i(this.node).jStore("cr", A.$new(((Math.floor(Math.random() * 101) + 1) % 2) ? "span" : "div").setProps({id: "crMz" + K}).jSetCss({
          display: "inline",
          overflow: "hidden",
          visibility: "visible",
          color: r[1],
          fontSize: r[2],
          fontWeight: r[3],
          fontFamily: "sans-serif",
          position: "absolute",
          top: 8,
          left: 8,
          margin: "auto",
          width: "auto",
          textAlign: "right",
          "line-height": "2em",
          zIndex: 2147483647
        }).changeContent(x(r[0])));
        if (i(i(this.node).jFetch("cr")).byTag("a")[0]) {
          i(i(i(this.node).jFetch("cr")).byTag("a")[0]).jAddEvent("tap btnclick", function (L) {
            L.stopDistribution();
            window.open(this.href)
          }).setProps({id: "mzCrA" + K})
        }
        A.addCSS("#" + this.id + " > figure.mz-figure > #" + ("crMz" + K) + ",#" + this.id + " > figure.mz-figure > #" + ("crMz" + K) + " > #" + ("mzCrA" + K) + ",html body .mz-expand > #" + ("crMz" + K) + " > #" + ("mzCrA" + K) + ",html body .mz-expand > #" + ("crMz" + K) + " > #" + ("mzCrA" + K), {
          display: "inline !important;",
          visibility: "visible !important;",
          zIndex: "2147483647 !important;",
          fontSize: r[2] + " !important;",
          color: r[1] + " !important;"
        }, "mz-runtime-css", true)
      }*/
      if ((I = ("" + this.option("zoomWidth")).match(/^([0-9]+)?(px|%)?$/))) {
        this.zoomBox.size.wunits = I[2] || "px";
        this.zoomBox.size.width = (parseFloat(I[1]) || "auto")
      }
      if ((I = ("" + this.option("zoomHeight")).match(/^([0-9]+)?(px|%)?$/))) {
        this.zoomBox.size.hunits = I[2] || "px";
        this.zoomBox.size.height = (parseFloat(I[1]) || "auto")
      }
      if ("magnifier" == this.zoomBox.mode) {
        this.node.jAddClass("mz-magnifier-zoom");
        this.zoomBox.node.jAddClass("mz-magnifier");
        if ("auto" === this.zoomBox.size.width) {
          this.zoomBox.size.wunits = "%";
          this.zoomBox.size.width = 70
        }
        if ("auto" === this.zoomBox.size.height) {
          this.zoomBox.size.hunits = "%"
        }
      } else {
        if (this.option("zoom-position").match(/^#/)) {
          if (this.zoomBox.custom = i(this.option("zoom-position").replace(/^#/, ""))) {
            if (i(this.zoomBox.custom).jGetSize().height > 50) {
              if ("auto" === this.zoomBox.size.width) {
                this.zoomBox.size.wunits = "%";
                this.zoomBox.size.width = 100
              }
              if ("auto" === this.zoomBox.size.height) {
                this.zoomBox.size.hunits = "%";
                this.zoomBox.size.height = 100
              }
            }
          } else {
            this.option("zoom-position", "right")
          }
        }
        if ("preview" == this.zoomBox.mode) {
          if ("auto" === this.zoomBox.size.width) {
            this.zoomBox.size.wunits = "px"
          }
          if ("auto" === this.zoomBox.size.height) {
            this.zoomBox.size.hunits = "px"
          }
        }
        if ("zoom" == this.zoomBox.mode) {
          if ("auto" === this.zoomBox.size.width || "inner" == this.option("zoom-position")) {
            this.zoomBox.size.wunits = "%";
            this.zoomBox.size.width = 100
          }
          if ("auto" === this.zoomBox.size.height || "inner" == this.option("zoom-position")) {
            this.zoomBox.size.hunits = "%";
            this.zoomBox.size.height = 100
          }
        }
        if ("inner" == this.option("zoom-position")) {
          this.node.jAddClass("mz-inner-zoom")
        }
      }
      this.zoomBox.position = this.zoomBox.custom ? "custom" : this.option("zoom-position");
      this.lens.border.x = parseFloat(this.lens.node.jGetCss("border-left-width") || "0");
      this.lens.border.y = parseFloat(this.lens.node.jGetCss("border-top-width") || "0");
      this.image.loadSmall(function () {
        if (2 !== this.image.small.state) {
          return
        }
        this.image.setCurNode("small");
        this.size = this.image.node.jGetSize();
        this.registerEvents();
        this.ready = true;
        if (true === this.option("lazyZoom")) {
          this.showHint()
        }
      }.jBind(this));
      if (true !== this.option("lazyZoom") || "always" == this.option("zoomOn")) {
        this.image.load(i(function (L) {
          this.setupZoom(L, true)
        }).jBind(this));
        this.loadTimer = i(this.showLoading).jBind(this).jDelay(400)
      }
      this.setupSelectors()
    }, stop: function () {
      this.unregisterEvents();
      if (this.zoomBox) {
        this.zoomBox.node.kill()
      }
      if (this.expandThumbs) {
        this.expandThumbs.stop();
        this.expandThumbs = null
      }
      if (this.expandBox) {
        this.expandBox.kill()
      }
      if (this.expanded) {
        i(A.browser.getDoc()).jSetCss({overflow: ""})
      }
      i(this.additionalImages).jEach(function (I) {
        i(I.origin).jRemoveClass("mz-thumb-selected").jRemoveClass(this.option("cssClass") || "mz-$dummy-css-class-to-jRemove$")
      }, this);
      if (this.originalImg) {
        this.placeholder.append(this.originalImg);
        if (this.originalImgSrc) {
          this.originalImg.setAttribute("src", this.originalImgSrc)
        }
      }
      if (this.originalTitle) {
        this.placeholder.setAttribute("title", this.originalTitle)
      }
      if (this.node) {
        this.node.kill()
      }
    }, setupZoom: function (J, K) {
      var I = this.image;
      if (2 !== J.zoom.state) {
        this.image = J;
        this.ready = true;
        this.zoomBox.enable(false);
        return
      }
      this.image = J;
      this.image.setCurNode(this.expanded ? "zoom" : "small");
      this.zoomBox.image.src = this.image.getURL("zoom");
      this.zoomBox.node.jRemoveClass("mz-preview");
      this.zoomBox.image.getAttribute("style");
      this.zoomBox.image.removeAttribute("style");
      this.zoomBox.node.jGetSize();
      setTimeout(i(function () {
        var M = this.zoomBox.image.jGetSize(), L;
        this.zoomSizeOrigin = this.image.jGetSize("zoom");
        if (M.width * M.height > 1 && M.width * M.height < this.zoomSizeOrigin.width * this.zoomSizeOrigin.height) {
          this.zoomSizeOrigin = M
        }
        this.zoomSize = A.detach(this.zoomSizeOrigin);
        if ("preview" == this.zoomBox.mode) {
          this.zoomBox.node.jAddClass("mz-preview")
        }
        this.setCaption();
        this.lens.image.src = this.image.node.currentSrc || this.image.node.src;
        this.zoomBox.enable(this.zoomBox.mode && !(this.expanded && "preview" == this.zoomBox.mode));
        this.ready = true;
        this.activateTimer = null;
        this.resizeCallback();
        this.node.jAddClass("mz-ready");
        this.hideLoading();
        if (I !== this.image) {
          u("onUpdate", this.id, I.origin, this.image.origin);
          if (this.nextImage) {
            L = this.nextImage;
            this.nextImage = null;
            this.update(L.image, L.onswipe)
          }
        } else {
          u("onZoomReady", this.id)
        }
        if (this.initEvent) {
          this.node.jCallEvent(this.initEvent.type, this.initEvent)
        } else {
          if (this.expanded && "always" == this.option("expandZoomOn")) {
            this.activate()
          } else {
            if (!!K) {
              this.showHint()
            }
          }
        }
      }).jBind(this), 256)
    }, setupSelectors: function () {
      var J = this.id, I, K;
      K = new RegExp("zoom\\-id(\\s+)?:(\\s+)?" + J + "($|;)");
      if (A.browser.features.query) {
        I = A.$A(document.querySelectorAll('[data-zoom-id="' + this.id + '"]'));
        I = i(I).concat(A.$A(document.querySelectorAll('[rel*="zoom-id"]')).filter(function (L) {
          return K.test(L.getAttribute("rel") || "")
        }))
      } else {
        I = A.$A(document.getElementsByTagName("A")).filter(function (L) {
          return J == L.getAttribute("data-zoom-id") || K.test(L.getAttribute("rel") || "")
        })
      }
      i(I).jEach(function (M) {
        var L, N;
        i(M).jAddEvent("click", function (O) {
          O.stopDefaults()
        });
        L = new l().parseNode(M, this.originalTitle);
        if (this.image.zoom.src.has(L.zoom.src) && this.image.small.src.has(L.small.src)) {
          i(L.origin).jAddClass("mz-thumb-selected");
          L = this.image;
          L.origin = M
        }
        if (!L.link && this.image.link) {
          L.link = this.image.link
        }
        N = i(function () {
          this.update(L)
        }).jBind(this);
        i(M).jAddEvent("mousedown", function (O) {
          if ("stopImmediatePropagation" in O) {
            O.stopImmediatePropagation()
          }
        }, 5);
        i(M).jAddEvent("tap " + ("hover" == this.option("selectorTrigger") ? "mouseover mouseout" : "btnclick"), i(function (P, O) {
          if (this.updateTimer) {
            clearTimeout(this.updateTimer)
          }
          this.updateTimer = false;
          if ("mouseover" == P.type) {
            this.updateTimer = i(N).jDelay(O)
          } else {
            if ("tap" == P.type || "btnclick" == P.type) {
              N()
            }
          }
        }).jBindAsEvent(this, 60)).jAddClass(this.option("cssClass")).jAddClass("mz-thumb");
        L.loadSmall();
        if (true !== this.option("lazyZoom")) {
          L.loadZoom()
        }
        this.additionalImages.push(L)
      }, this)
    }, update: function (I, J) {
      if (!this.ready) {
        this.nextImage = {image: I, onswipe: J};
        return
      }
      if (!I || I === this.image) {
        return false
      }
      this.deactivate(null, true);
      this.ready = false;
      this.node.jRemoveClass("mz-ready");
      this.loadTimer = i(this.showLoading).jBind(this).jDelay(400);
      I.load(i(function (Q) {
        var K, R, P, M, L, O, N = (A.browser.ieMode < 10) ? "jGetSize" : "getBoundingClientRect";
        this.hideLoading();
        Q.setCurNode("small");
        if (!Q.node) {
          this.ready = true;
          this.node.jAddClass("mz-ready");
          return
        }
        this.setActiveThumb(Q);
        K = this.image.node[N]();
        if (this.expanded) {
          Q.setCurNode("zoom");
          P = A.$new("div").jAddClass("mz-expand-bg");
          if (A.browser.features.cssFilters || A.browser.ieMode < 10) {
            P.append(A.$new("img", {src: Q.getURL("zoom")}).jSetCss({opacity: 0}))
          } else {
            P.append(new A.SVGImage(Q.node).blur(b).getNode().jSetCss({opacity: 0}))
          }
          i(P).jSetCss({"z-index": -99}).jAppendTo(this.expandBox)
        }
        if (this.expanded && "zoom" === this.zoomBox.mode && "always" === this.option("expandZoomOn")) {
          i(Q.node).jSetCss({opacity: 0}).jAppendTo(this.node);
          R = K;
          L = [Q.node, this.image.node];
          O = [{opacity: [0, 1]}, {opacity: [1, 0]}];
          i(Q.node).jSetCss({"max-width": Math.min(Q.jGetSize("zoom").width, this.expandMaxWidth()), "max-height": Math.min(Q.jGetSize("zoom").height, this.expandMaxHeight())})
        } else {
          this.node.jSetCss({height: this.node[N]().height});
          this.image.node.jSetCss({position: "absolute", top: 0, left: 0, bottom: 0, right: 0, width: "100%", height: "100%", "max-width": "", "max-height": ""});
          i(Q.node).jSetCss({
            "max-width": Math.min(Q.jGetSize(this.expanded ? "zoom" : "small").width, this.expanded ? this.expandMaxWidth() : Infinity),
            "max-height": Math.min(Q.jGetSize(this.expanded ? "zoom" : "small").height, this.expanded ? this.expandMaxHeight() : Infinity),
            position: "relative",
            top: 0,
            left: 0,
            opacity: 0,
            transform: ""
          }).jAppendTo(this.node);
          R = i(Q.node)[N]();
          if (!J) {
            i(Q.node).jSetCss({"min-width": K.width, height: K.height, "max-width": K.width, "max-height": ""})
          }
          this.node.jSetCss({height: "", overflow: ""}).jGetSize();
          i(Q.node).jGetSize();
          L = [Q.node, this.image.node];
          O = [A.extend({opacity: [0, 1]}, J ? {scale: [0.6, 1]} : {
            "min-width": [K.width, R.width],
            "max-width": [K.width, R.width],
            height: [K.height, R.height]
          }), {opacity: [1, 0]}]
        }
        if (this.expanded) {
          if (this.expandBg.firstChild && P.firstChild) {
            M = i(this.expandBg.firstChild).jGetCss("opacity");
            if (A.browser.gecko) {
              L = L.concat([P.firstChild]);
              O = O.concat([{opacity: [0.0001, M]}])
            } else {
              L = L.concat([P.firstChild, this.expandBg.firstChild]);
              O = O.concat([{opacity: [0.0001, M]}, {opacity: [M, 0.0001]}])
            }
          }
        }
        new A.PFX(L, {
          duration: (J || this.option("transitionEffect")) ? J ? 400 : 350 : 0,
          transition: J ? "cubic-bezier(0.175, 0.885, 0.320, 1.275)" : (K.width == R.width) ? "linear" : "cubic-bezier(0.25, .1, .1, 1)",
          onComplete: i(function () {
            this.image.node.jRemove().getAttribute("style");
            this.image.node.removeAttribute("style");
            i(Q.node).jSetCss(this.expanded ? {width: "auto", height: "auto"} : {width: "", height: ""}).jSetCss({
              "min-width": "",
              "min-height": "",
              opacity: "",
              "max-width": Math.min(Q.jGetSize(this.expanded ? "zoom" : "small").width, this.expanded ? this.expandMaxWidth() : Infinity),
              "max-height": Math.min(Q.jGetSize(this.expanded ? "zoom" : "small").height, this.expanded ? this.expandMaxHeight() : Infinity)
            });
            if (this.expanded) {
              this.expandBg.jRemove();
              this.expandBg = undefined;
              this.expandBg = P.jSetCssProp("z-index", -100);
              i(this.expandBg.firstChild).jSetCss({opacity: ""});
              if (this.expandCaption) {
                if (Q.caption) {
                  if (Q.link) {
                    this.expandCaption.changeContent("").append(A.$new("a", {href: Q.link}).jAddEvent("tap btnclick", this.openLink.jBind(this)).changeContent(Q.caption))
                  } else {
                    this.expandCaption.changeContent(Q.caption).jAddClass("mz-show")
                  }
                } else {
                  this.expandCaption.jRemoveClass("mz-show")
                }
              }
            }
            this.setupZoom(Q)
          }).jBind(this),
          onBeforeRender: i(function (S, T) {
            if (undefined !== S.scale) {
              T.jSetCssProp("transform", "scale(" + S.scale + ")")
            }
          })
        }).start(O)
      }).jBind(this))
    }, setActiveThumb: function (J) {
      var I = false;
      i(this.additionalImages).jEach(function (K) {
        i(K.origin).jRemoveClass("mz-thumb-selected");
        if (K === J) {
          I = true
        }
      });
      if (I && J.origin) {
        i(J.origin).jAddClass("mz-thumb-selected")
      }
      if (this.expandThumbs) {
        this.expandThumbs.selectItem(J.selector)
      }
    }, setCaption: function (I) {
      if (this.image.caption && "off" !== this.option("zoomCaption") && "magnifier" !== this.zoomBox.mode) {
        if (!this.zoomBox.caption) {
          this.zoomBox.caption = A.$new("div", {"class": "mz-caption"}).jAppendTo(this.zoomBox.node.jAddClass("caption-" + this.option("zoomCaption")))
        }
        this.zoomBox.caption.changeContent(this.image.caption)
      }
    }, showHint: function (I, L, J) {
      var K;
      if (!this.expanded) {
        if (this.hintRuns <= 0) {
          return
        }
        if (true !== J) {
          this.hintRuns--
        }
      }
      if (undefined === L || null === L) {
        if (!this.zoomBox.active && !this.zoomBox.activating) {
          if (this.option("zoomMode") && (this.zoomBox.enabled || !this.image.loaded())) {
            if ("hover" == this.zoomBox.trigger) {
              L = this.option("textHoverZoomHint")
            } else {
              if ("click" == this.zoomBox.trigger) {
                L = this.option("textClickZoomHint")
              }
            }
          } else {
            L = this.option("expand") ? this.option("textExpandHint") : ""
          }
        } else {
          L = this.option("expand") ? this.option("textExpandHint") : ""
        }
      }
      if (!L) {
        this.hideHint();
        return
      }
      K = this.node;
      if (!this.hint) {
        this.hint = A.$new("div", {"class": "mz-hint"});
        this.hintMessage = A.$new("span", {"class": "mz-hint-message"}).append(document.createTextNode(L)).jAppendTo(this.hint);
        i(this.hint).jAppendTo(this.node)
      } else {
        i(this.hintMessage).changeContent(L)
      }
      this.hint.jSetCss({"transition-delay": ""}).jRemoveClass("mz-hint-hidden");
      if (this.expanded) {
        K = this.expandFigure
      } else {
        if ((this.zoomBox.active || this.zoomBox.activating) && "magnifier" !== this.zoomBox.mode && "inner" == this.zoomBox.position) {
          K = this.zoomBox.node
        }
      }
      if (true === I) {
        setTimeout(i(function () {
          this.hint.jAddClass("mz-hint-hidden")
        }).jBind(this), 16)
      }
      this.hint.jAppendTo(K)
    }, hideHint: function () {
      if (this.hint) {
        this.hint.jSetCss({"transition-delay": "0ms"}).jAddClass("mz-hint-hidden")
      }
    }, showLoading: function () {
      if (!this.loadingBox) {
        this.loadingBox = A.$new("div", {"class": "mz-loading"});
        this.node.append(this.loadingBox);
        this.loadingBox.jGetSize()
      }
      this.loadingBox.jAddClass("shown")
    }, hideLoading: function () {
      clearTimeout(this.loadTimer);
      this.loadTimer = null;
      if (this.loadingBox) {
        i(this.loadingBox).jRemoveClass("shown")
      }
    }, setSize: function (K, O) {
      var N = A.detach(this.zoomBox.size), M = (!this.expanded && this.zoomBox.custom) ? i(this.zoomBox.custom).jGetSize() : {width: 0, height: 0}, J, I, L = this.size, P = {
        x: 0,
        y: 0
      };
      O = O || this.zoomBox.position;
      this.normalSize = this.image.node.jGetSize();
      this.size = this.image.node.jGetSize();
      this.boundaries = this.image.node.getBoundingClientRect();
      if (!M.height) {
        M = this.size
      }
      if (false === this.option("upscale") || false === this.zoomBox.mode || "preview" === this.zoomBox.mode) {
        K = false
      }
      if ("preview" == this.zoomBox.mode) {
        if ("auto" === N.width) {
          N.width = this.zoomSizeOrigin.width
        }
        if ("auto" === N.height) {
          N.height = this.zoomSizeOrigin.height
        }
      }
      if (this.expanded && "magnifier" == this.zoomBox.mode) {
        N.width = 70;
        N.height = "auto"
      }
      if ("magnifier" == this.zoomBox.mode && "auto" === N.height) {
        this.zoomBox.width = parseFloat(N.width / 100) * Math.min(M.width, M.height);
        this.zoomBox.height = this.zoomBox.width
      } else {
        if ("zoom" == this.zoomBox.mode && "inner" == O) {
          this.size = this.node.jGetSize();
          M = this.size;
          this.boundaries = this.node.getBoundingClientRect();
          this.zoomBox.width = M.width;
          this.zoomBox.height = M.height
        } else {
          this.zoomBox.width = ("%" === N.wunits) ? parseFloat(N.width / 100) * M.width : parseInt(N.width);
          this.zoomBox.height = ("%" === N.hunits) ? parseFloat(N.height / 100) * M.height : parseInt(N.height)
        }
      }
      if ("preview" == this.zoomBox.mode) {
        I = Math.min(Math.min(this.zoomBox.width / this.zoomSizeOrigin.width, this.zoomBox.height / this.zoomSizeOrigin.height), 1);
        this.zoomBox.width = this.zoomSizeOrigin.width * I;
        this.zoomBox.height = this.zoomSizeOrigin.height * I
      }
      this.zoomBox.width = Math.ceil(this.zoomBox.width);
      this.zoomBox.height = Math.ceil(this.zoomBox.height);
      this.zoomBox.aspectRatio = this.zoomBox.width / this.zoomBox.height;
      this.zoomBox.node.jSetCss({width: this.zoomBox.width, height: this.zoomBox.height});
      if (K) {
        M = this.expanded ? this.expandBox.jGetSize() : this.zoomBox.node.jGetSize();
        if (!this.expanded && (this.normalSize.width * this.normalSize.height) / (this.zoomSizeOrigin.width * this.zoomSizeOrigin.height) > 0.8) {
          this.zoomSize.width = 1.5 * this.zoomSizeOrigin.width;
          this.zoomSize.height = 1.5 * this.zoomSizeOrigin.height
        } else {
          this.zoomSize = A.detach(this.zoomSizeOrigin)
        }
      }
      if (false !== this.zoomBox.mode && !this.zoomBox.active && !(this.expanded && "always" == this.option("expandZoomOn"))) {
        if ((this.normalSize.width * this.normalSize.height) / (this.zoomSize.width * this.zoomSize.height) > 0.8) {
          this.zoomSize = A.detach(this.zoomSizeOrigin);
          this.zoomBox.enable(false)
        } else {
          this.zoomBox.enable(true)
        }
      }
      this.zoomBox.image.jSetCss({width: this.zoomSize.width, height: this.zoomSize.height});
      J = this.zoomBox.node.getInnerSize();
      this.zoomBox.innerWidth = Math.ceil(J.width);
      this.zoomBox.innerHeight = Math.ceil(J.height);
      this.lens.width = Math.ceil(this.zoomBox.innerWidth / (this.zoomSize.width / this.size.width));
      this.lens.height = Math.ceil(this.zoomBox.innerHeight / (this.zoomSize.height / this.size.height));
      this.lens.node.jSetCss({width: this.lens.width, height: this.lens.height});
      this.lens.image.jSetCss(this.size);
      A.extend(this.lens, this.lens.node.jGetSize());
      if (this.zoomBox.active) {
        clearTimeout(this.moveTimer);
        this.moveTimer = null;
        if (this.lens.innertouch) {
          this.lens.pos.x *= (this.size.width / L.width);
          this.lens.pos.y *= (this.size.height / L.height);
          P.x = this.lens.spos.x;
          P.y = this.lens.spos.y
        } else {
          P.x = this.boundaries.left + this.lens.width / 2 + (this.lens.pos.x * (this.size.width / L.width));
          P.y = this.boundaries.top + this.lens.height / 2 + (this.lens.pos.y * (this.size.height / L.height))
        }
        this.animate(null, P)
      }
    }, reflowZoom: function (M) {
      var P, O, I, N, L, K, J = i(this.node).jFetch("cr");
      I = a(5);
      L = this.zoomBox.position;
      N = this.expanded ? "inner" : this.zoomBox.custom ? "custom" : this.option("zoom-position");
      K = this.expanded && "zoom" == this.zoomBox.mode ? this.expandBox : document.body;
      if (this.expanded) {
        I.y = 0;
        I.x = 0
      }
      if (!M) {
        this.setSize(true, N)
      }
      P = this.boundaries.top;
      if ("magnifier" !== this.zoomBox.mode) {
        if (M) {
          this.setSize(false);
          return
        }
        switch (N) {
          case"inner":
          case"custom":
            P = 0;
            O = 0;
            break;
          case"top":
            P = this.boundaries.top - this.zoomBox.height - this.option("zoom-distance");
            if (I.top > P) {
              P = this.boundaries.bottom + this.option("zoom-distance");
              N = "bottom"
            }
            O = this.boundaries.left;
            break;
          case"bottom":
            P = this.boundaries.bottom + this.option("zoom-distance");
            if (I.bottom < P + this.zoomBox.height) {
              P = this.boundaries.top - this.zoomBox.height - this.option("zoom-distance");
              N = "top"
            }
            O = this.boundaries.left;
            break;
          case"left":
            O = this.boundaries.left - this.zoomBox.width - this.option("zoom-distance");
            if (I.left > O && I.right >= this.boundaries.right + this.option("zoom-distance") + this.zoomBox.width) {
              O = this.boundaries.right + this.option("zoom-distance");
              N = "right"
            }
            break;
          case"right":
          default:
            O = this.boundaries.right + this.option("zoom-distance");
            if (I.right < O + this.zoomBox.width && I.left <= this.boundaries.left - this.zoomBox.width - this.option("zoom-distance")) {
              O = this.boundaries.left - this.zoomBox.width - this.option("zoom-distance");
              N = "left"
            }
            break
        }
        switch (this.option("zoom-position")) {
          case"top":
          case"bottom":
            if (I.top > P || I.bottom < P + this.zoomBox.height) {
              N = "inner"
            }
            break;
          case"left":
          case"right":
            if (I.left > O || I.right < O + this.zoomBox.width) {
              N = "inner"
            }
            break
        }
        this.zoomBox.position = N;
        if (!this.zoomBox.activating && !this.zoomBox.active) {
          if (A.browser.mobile && !this.expanded && "zoom" == this.zoomBox.mode) {
            if (this.option("expand")) {
              this.zoomBox.enable("inner" !== N)
            } else {
              if ("click" !== this.option("zoomOn")) {
                this.zoomBox.trigger = "inner" === N ? "click" : this.option("zoomOn");
                this.unregisterActivateEvent();
                this.unregisterDeactivateEvent();
                this.registerActivateEvent("click" === this.zoomBox.trigger);
                this.registerDeactivateEvent("click" === this.zoomBox.trigger && !this.option("expand"))
              }
            }
            this.showHint(false, null, true)
          }
          return
        }
        this.setSize(false);
        if (M) {
          return
        }
        if ("custom" == N) {
          K = this.zoomBox.custom;
          I.y = 0;
          I.x = 0
        }
        if ("inner" == N) {
          if ("preview" !== this.zoomBox.mode) {
            this.zoomBox.node.jAddClass("mz-inner");
            this.node.jAddClass("mz-inner-zoom")
          }
          this.lens.hide();
          P = this.boundaries.top + I.y;
          O = this.boundaries.left + I.x;
          if (!this.expanded && A.browser.ieMode && A.browser.ieMode < 11) {
            P = 0;
            O = 0;
            K = this.node
          }
        } else {
          P += I.y;
          O += I.x;
          this.node.jRemoveClass("mz-inner-zoom");
          this.zoomBox.node.jRemoveClass("mz-inner")
        }
        this.zoomBox.node.jSetCss({top: P, left: O})
      } else {
        this.setSize(false);
        K = this.node
      }
      this.zoomBox.node[this.expanded ? "jAddClass" : "jRemoveClass"]("mz-expanded");
      if (!this.expanded && J) {
        J.jAppendTo("zoom" == this.zoomBox.mode && "inner" == N ? this.zoomBox.node : this.node, ((Math.floor(Math.random() * 101) + 1) % 2) ? "top" : "bottom")
      }
      this.zoomBox.node.jAppendTo(K)
    }, changeZoomLevel: function (O) {
      var K, I, M, L, N = false, J = O.isMouse ? 5 : 3 / 54;
      i(O).stop();
      J = (100 + J * Math.abs(O.deltaY)) / 100;
      if (O.deltaY < 0) {
        J = 1 / J
      }
      if ("magnifier" == this.zoomBox.mode) {
        I = Math.max(100, Math.round(this.zoomBox.width * J));
        I = Math.min(I, this.size.width * 0.9);
        M = I / this.zoomBox.aspectRatio;
        this.zoomBox.width = Math.ceil(I);
        this.zoomBox.height = Math.ceil(M);
        this.zoomBox.node.jSetCss({width: this.zoomBox.width, height: this.zoomBox.height});
        K = this.zoomBox.node.getInnerSize();
        this.zoomBox.innerWidth = Math.ceil(K.width);
        this.zoomBox.innerHeight = Math.ceil(K.height);
        N = true
      } else {
        if (!this.expanded && "zoom" == this.zoomBox.mode) {
          I = Math.max(50, Math.round(this.lens.width * J));
          I = Math.min(I, this.size.width * 0.9);
          M = I / this.zoomBox.aspectRatio;
          this.zoomSize.width = Math.ceil((this.zoomBox.innerWidth / I) * this.size.width);
          this.zoomSize.height = Math.ceil((this.zoomBox.innerHeight / M) * this.size.height);
          this.zoomBox.image.jSetCss({width: this.zoomSize.width, height: this.zoomSize.height})
        } else {
          return
        }
      }
      L = i(window).jGetScroll();
      this.lens.width = Math.ceil(this.zoomBox.innerWidth / (this.zoomSize.width / this.size.width));
      this.lens.height = Math.ceil(this.zoomBox.innerHeight / (this.zoomSize.height / this.size.height));
      this.lens.node.jSetCss({width: this.lens.width, height: this.lens.height});
      A.extend(this.lens, this.lens.node.jGetSize());
      if (this.zoomBox.active) {
        clearTimeout(this.moveTimer);
        this.moveTimer = null;
        if (N) {
          this.moveTimer = true
        }
        this.animate(null, {x: O.x - L.x, y: O.y - L.y});
        if (N) {
          this.moveTimer = null
        }
      }
    }, registerActivateEvent: function (K) {
      var J;
      var I = K ? "dbltap btnclick" : "touchstart" + (window.navigator.pointerEnabled ? " pointerdown" : window.navigator.msPointerEnabled ? " MSPointerDown" : "") + (window.navigator.pointerEnabled ? " pointermove" : window.navigator.msPointerEnabled ? " MSPointerMove" : " mousemove");
      var L = this.node.jFetch("mz:handlers:activate:fn", (!K) ? i(function (M) {
        if (c(M) && !h(M)) {
          return
        }
        if (M && "touch" === M.pointerType && "pointerdown" !== M.type) {
          return
        }
        J = (A.browser.ieMode < 9) ? A.extend({}, M) : M;
        if (!this.activateTimer) {
          clearTimeout(this.activateTimer);
          this.activateTimer = setTimeout(i(function () {
            this.activate(J)
          }).jBind(this), 120)
        }
      }).jBindAsEvent(this) : i(this.activate).jBindAsEvent(this));
      this.node.jStore("mz:handlers:activate:event", I).jAddEvent(I, L, 10)
    }, unregisterActivateEvent: function (J) {
      var I = this.node.jFetch("mz:handlers:activate:event"), K = this.node.jFetch("mz:handlers:activate:fn");
      this.node.jRemoveEvent(I, K);
      this.node.jDel("mz:handlers:activate:fn")
    }, registerDeactivateEvent: function (J) {
      var I = J ? "dbltap btnclick" : "touchend" + (window.navigator.pointerEnabled ? " pointerup pointerout" : window.navigator.msPointerEnabled ? " MSPointerUp MSPointerOut" : " mouseout");
      var K = this.node.jFetch("mz:handlers:deactivate:fn", i(function (L) {
        if (c(L) && !h(L)) {
          return
        }
        if (this.zoomBox.node !== L.getRelated() && !(("inner" == this.zoomBox.position || "magnifier" == this.zoomBox.mode) && this.zoomBox.node.hasChild(L.getRelated())) && !this.node.hasChild(L.getRelated())) {
          this.deactivate(L)
        }
      }).jBindAsEvent(this));
      this.node.jStore("mz:handlers:deactivate:event", I).jAddEvent(I, K, 20)
    }, unregisterDeactivateEvent: function () {
      var I = this.node.jFetch("mz:handlers:deactivate:event"), J = this.node.jFetch("mz:handlers:deactivate:fn");
      this.node.jRemoveEvent(I, J);
      this.node.jDel("mz:handlers:deactivate:fn")
    }, registerEvents: function () {
      this.moveBind = this.move.jBind(this);
      this.node.jAddEvent(["touchstart", window.navigator.pointerEnabled ? "pointerdown" : "MSPointerDown"], i(function (I) {
        if ((A.browser.androidBrowser || "android" === A.browser.platform && A.browser.gecko) && this.option("zoomMode") && "click" !== this.option("zoomOn") && "touchstart" === I.type) {
          I.stopDefaults();
          if (A.browser.gecko) {
            I.stopDistribution()
          }
        }
        if (!this.zoomBox.active) {
          return
        }
        if ("inner" === this.zoomBox.position) {
          this.lens.spos = I.getClientXY()
        }
      }).jBindAsEvent(this), 10);
      this.node.jAddEvent(["touchend", window.navigator.pointerEnabled ? "pointerup" : "MSPointerUp"], i(function (I) {
        if (c(I) && h(I)) {
          this.lens.touchmovement = false
        }
      }).jBindAsEvent(this), 10);
      this.node.jAddEvent("touchmove " + ("android" === A.browser.platform ? "" : window.navigator.pointerEnabled ? "pointermove" : window.navigator.msPointerEnabled ? "MSPointerMove" : "mousemove"), i(this.animate).jBindAsEvent(this));
      if (this.option("zoomMode")) {
        this.registerActivateEvent("click" === this.option("zoomOn"));
        this.registerDeactivateEvent("click" === this.option("zoomOn") && !this.option("expand"))
      }
      this.node.jAddEvent("mousedown", function (I) {
        I.stopDistribution()
      }, 10).jAddEvent("btnclick", i(function (I) {
        this.node.jRaiseEvent("MouseEvent", "click");
        if (this.expanded) {
          this.expandBox.jCallEvent("btnclick", I)
        }
      }).jBind(this), 15);
      if (this.option("expand")) {
        this.node.jAddEvent("tap btnclick", i(this.expand).jBindAsEvent(this), 15)
      } else {
        this.node.jAddEvent("tap btnclick", i(this.openLink).jBindAsEvent(this), 15)
      }
      if (this.additionalImages.length > 1) {
        this.swipe()
      }
      if (!A.browser.mobile && this.option("variableZoom")) {
        this.node.jAddEvent("mousescroll", this.changeZoomLevel.jBindAsEvent(this))
      }
      i(window).jAddEvent(A.browser.mobile ? "resize" : "resize scroll", this.onResize)
    }, unregisterEvents: function () {
      if (this.node) {
        this.node.jRemoveEvent("mousescroll")
      }
      i(window).jRemoveEvent("resize scroll", this.onResize);
      i(this.additionalImages).jEach(function (I) {
        i(I.origin).jClearEvents()
      })
    }, activate: function (O) {
      var P, N, L, M, I, J = 0, K = 0;
      if (!this.ready || !this.zoomBox.enabled || this.zoomBox.active || this.zoomBox.activating) {
        if (!this.image.loaded()) {
          if (O) {
            this.initEvent = e(O);
            O.stopQueue()
          }
          this.image.load(this.setupZoom.jBind(this));
          if (!this.loadTimer) {
            this.loadTimer = i(this.showLoading).jBind(this).jDelay(400)
          }
        }
        return
      }
      if (O && "pointermove" == O.type && "touch" == O.pointerType) {
        return
      }
      if (!this.option("zoomMode") && this.option("expand") && !this.expanded) {
        this.zoomBox.active = true;
        return
      }
      this.zoomBox.activating = true;
      if (this.expanded && "zoom" == this.zoomBox.mode) {
        M = this.image.node.jGetRect();
        this.expandStage.jAddClass("mz-zoom-in");
        I = this.expandFigure.jGetRect();
        K = ((M.left + M.right) / 2 - (I.left + I.right) / 2);
        J = ((M.top + M.bottom) / 2 - (I.top + I.bottom) / 2)
      }
      this.zoomBox.image.jRemoveEvent("transitionend");
      this.zoomBox.node.jRemoveClass("mz-deactivating").jRemoveEvent("transitionend");
      this.zoomBox.node.jAddClass("mz-activating");
      this.node.jAddClass("mz-activating");
      this.reflowZoom();
      N = ("zoom" == this.zoomBox.mode) ? this.zoomBox.position : this.zoomBox.mode;
      if (A.browser.features.transition && !(this.expanded && "always" == this.option("expandZoomOn"))) {
        if ("inner" == N) {
          L = this.image.node.jGetSize();
          this.zoomBox.image.jSetCss({transform: "translate3d(0," + J + "px, 0) scale(" + L.width / this.zoomSize.width + ", " + L.height / this.zoomSize.height + ")"}).jGetSize();
          this.zoomBox.image.jAddEvent("transitionend", i(function () {
            this.zoomBox.image.jRemoveEvent("transitionend");
            this.zoomBox.node.jRemoveClass("mz-activating mz-p-" + N);
            this.zoomBox.activating = false;
            this.zoomBox.active = true
          }).jBind(this));
          this.zoomBox.node.jAddClass("mz-p-" + N).jGetSize();
          if (!A.browser.mobile && A.browser.chrome && ("chrome" === A.browser.uaName || "opera" === A.browser.uaName)) {
            this.zoomBox.activating = false;
            this.zoomBox.active = true
          }
        } else {
          this.zoomBox.node.jAddEvent("transitionend", i(function () {
            this.zoomBox.node.jRemoveEvent("transitionend");
            this.zoomBox.node.jRemoveClass("mz-activating mz-p-" + N)
          }).jBind(this));
          this.zoomBox.node.jAddClass("mz-p-" + N).jGetSize();
          this.zoomBox.node.jRemoveClass("mz-p-" + N);
          this.zoomBox.activating = false;
          this.zoomBox.active = true
        }
      } else {
        this.zoomBox.node.jRemoveClass("mz-activating");
        this.zoomBox.activating = false;
        this.zoomBox.active = true
      }
      if (!this.expanded) {
        this.showHint(true)
      }
      if (O) {
        O.stop().stopQueue();
        P = O.getClientXY();
        if ("magnifier" == this.zoomBox.mode && (/tap/i).test(O.type)) {
          P.y -= this.zoomBox.height / 2 + 10
        }
        if ("inner" == N && ((/tap/i).test(O.type) || c(O))) {
          this.lens.pos = {x: 0, y: 0};
          P.x = -(P.x - this.boundaries.left - this.size.width / 2) * (this.zoomSize.width / this.size.width);
          P.y = -(P.y - this.boundaries.top - this.size.height / 2) * (this.zoomSize.height / this.size.height)
        }
      } else {
        P = {x: this.boundaries.left + (this.boundaries.right - this.boundaries.left) / 2, y: this.boundaries.top + (this.boundaries.bottom - this.boundaries.top) / 2};
        if (A.browser.mobile && this.expanded && "always" === this.option("expandZoomOn")) {
          this.lens.innertouch = true;
          this.lens.pos = {x: 0, y: 0};
          P.x = -(P.x - this.boundaries.left - this.size.width / 2) * (this.zoomSize.width / this.size.width);
          P.y = -(P.y - this.boundaries.top - this.size.height / 2) * (this.zoomSize.height / this.size.height)
        }
      }
      this.node.jRemoveClass("mz-activating").jAddClass("mz-active");
      P.x += -K;
      P.y += -J;
      this.lens.spos = {x: 0, y: 0};
      this.lens.dx = 0;
      this.lens.dy = 0;
      this.animate(O, P, true);
      u("onZoomIn", this.id)
    }, deactivate: function (K, P) {
      var N, L, I, J, M = 0, O = 0, Q = this.zoomBox.active;
      this.initEvent = null;
      if (!this.ready) {
        return
      }
      if (K && "pointerout" == K.type && "touch" == K.pointerType) {
        return
      }
      clearTimeout(this.moveTimer);
      this.moveTimer = null;
      clearTimeout(this.activateTimer);
      this.activateTimer = null;
      this.zoomBox.activating = false;
      this.zoomBox.active = false;
      if (true !== P && !this.expanded) {
        if (Q) {
          if (A.browser.mobile && !this.expanded && "zoom" == this.zoomBox.mode) {
            this.reflowZoom()
          } else {
            this.showHint()
          }
        }
      }
      if (!this.zoomBox.enabled) {
        return
      }
      if (K) {
        K.stop()
      }
      this.zoomBox.image.jRemoveEvent("transitionend");
      this.zoomBox.node.jRemoveClass("mz-activating").jRemoveEvent("transitionend");
      if (this.expanded) {
        J = this.expandFigure.jGetRect();
        if ("always" !== this.option("expandZoomOn")) {
          this.expandStage.jRemoveClass("mz-zoom-in")
        }
        this.image.node.jSetCss({"max-height": this.expandMaxHeight()});
        I = this.image.node.jGetRect();
        O = ((I.left + I.right) / 2 - (J.left + J.right) / 2);
        M = ((I.top + I.bottom) / 2 - (J.top + J.bottom) / 2)
      }
      N = ("zoom" == this.zoomBox.mode) ? this.zoomBox.position : this.zoomBox.mode;
      if (A.browser.features.transition && K && !(this.expanded && "always" == this.option("expandZoomOn"))) {
        if ("inner" == N) {
          this.zoomBox.image.jAddEvent("transitionend", i(function () {
            this.zoomBox.image.jRemoveEvent("transitionend");
            this.node.jRemoveClass("mz-active");
            setTimeout(i(function () {
              this.zoomBox.hide()
            }).jBind(this), 32)
          }).jBind(this));
          L = this.image.node.jGetSize();
          this.zoomBox.node.jAddClass("mz-deactivating mz-p-" + N).jGetSize();
          this.zoomBox.image.jSetCss({transform: "translate3d(0," + M + "px,0) scale(" + L.width / this.zoomSize.width + ", " + L.height / this.zoomSize.height + ")"})
        } else {
          this.zoomBox.node.jAddEvent("transitionend", i(function () {
            this.zoomBox.hide();
            this.node.jRemoveClass("mz-active")
          }).jBind(this));
          this.zoomBox.node.jGetCss("opacity");
          this.zoomBox.node.jAddClass("mz-deactivating mz-p-" + N);
          this.node.jRemoveClass("mz-active")
        }
      } else {
        this.zoomBox.hide();
        this.node.jRemoveClass("mz-active")
      }
      this.lens.dx = 0;
      this.lens.dy = 0;
      this.lens.spos = {x: 0, y: 0};
      this.lens.hide();
      if (Q) {
        u("onZoomOut", this.id)
      }
    }, animate: function (S, R, Q) {
      var K = R, M, L, O = 0, J, N = 0, I, T, P = false;
      if (!this.zoomBox.active && !Q) {
        return
      }
      if (S) {
        i(S).stopDefaults().stopDistribution();
        if (c(S) && !h(S)) {
          return
        }
        P = (/tap/i).test(S.type) || c(S);
        if (P && !this.lens.touchmovement) {
          this.lens.touchmovement = P
        }
        if (!K) {
          K = S.getClientXY()
        }
      }
      if ("preview" == this.zoomBox.mode) {
        return
      }
      if ("zoom" == this.zoomBox.mode && "inner" === this.zoomBox.position && (S && P || !S && this.lens.innertouch)) {
        this.lens.innertouch = true;
        M = this.lens.pos.x + (K.x - this.lens.spos.x);
        L = this.lens.pos.y + (K.y - this.lens.spos.y);
        this.lens.spos = K;
        O = Math.min(0, this.zoomBox.innerWidth - this.zoomSize.width) / 2;
        J = -O;
        N = Math.min(0, this.zoomBox.innerHeight - this.zoomSize.height) / 2;
        I = -N
      } else {
        this.lens.innertouch = false;
        if ("magnifier" == this.zoomBox.mode) {
          K.y = Math.max(this.boundaries.top, Math.min(K.y, this.boundaries.bottom));
          K.x = Math.max(this.boundaries.left, Math.min(K.x, this.boundaries.right))
        }
        M = K.x - this.boundaries.left;
        L = K.y - this.boundaries.top;
        J = this.size.width - this.lens.width;
        I = this.size.height - this.lens.height;
        M -= this.lens.width / 2;
        L -= this.lens.height / 2
      }
      if ("magnifier" !== this.zoomBox.mode) {
        M = Math.max(O, Math.min(M, J));
        L = Math.max(N, Math.min(L, I))
      }
      this.lens.pos.x = M = Math.round(M);
      this.lens.pos.y = L = Math.round(L);
      if ("zoom" == this.zoomBox.mode && "inner" != this.zoomBox.position) {
        if (A.browser.features.transform) {
          this.lens.node.jSetCss({transform: "translate(" + this.lens.pos.x + "px," + this.lens.pos.y + "px)"});
          this.lens.image.jSetCss({transform: "translate(" + -(this.lens.pos.x + this.lens.border.x) + "px, " + -(this.lens.pos.y + this.lens.border.y) + "px)"})
        } else {
          this.lens.node.jSetCss({top: this.lens.pos.y, left: this.lens.pos.x});
          this.lens.image.jSetCss({top: -(this.lens.pos.y + this.lens.border.y), left: -(this.lens.pos.x + this.lens.border.x)})
        }
      }
      if ("magnifier" == this.zoomBox.mode) {
        if (this.lens.touchmovement && !(S && "dbltap" == S.type)) {
          K.y -= this.zoomBox.height / 2 + 10
        }
        this.zoomBox.node.jSetCss({top: K.y - this.boundaries.top - this.zoomBox.height / 2, left: K.x - this.boundaries.left - this.zoomBox.width / 2})
      }
      if (!this.moveTimer) {
        this.lens.dx = 0;
        this.lens.dy = 0;
        this.move(1)
      }
    }, move: function (K) {
      var J, I;
      if (!isFinite(K)) {
        if (this.lens.innertouch) {
          K = this.lens.touchmovement ? 0.4 : 0.16
        } else {
          K = this.option("smoothing") ? 0.2 : this.lens.touchmovement ? 0.4 : 0.8
        }
      }
      J = ((this.lens.pos.x - this.lens.dx) * K);
      I = ((this.lens.pos.y - this.lens.dy) * K);
      this.lens.dx += J;
      this.lens.dy += I;
      if (!this.moveTimer || Math.abs(J) > 0.000001 || Math.abs(I) > 0.000001) {
        this.zoomBox.image.jSetCss(A.browser.features.transform ? {transform: g + (this.lens.innertouch ? this.lens.dx : -(this.lens.dx * (this.zoomSize.width / this.size.width) - Math.max(0, this.zoomSize.width - this.zoomBox.innerWidth) / 2)) + "px," + (this.lens.innertouch ? this.lens.dy : -(this.lens.dy * (this.zoomSize.height / this.size.height) - Math.max(0, this.zoomSize.height - this.zoomBox.innerHeight) / 2)) + "px" + C + " scale(1)"} : {
          left: -(this.lens.dx * (this.zoomSize.width / this.size.width) + Math.min(0, this.zoomSize.width - this.zoomBox.innerWidth) / 2),
          top: -(this.lens.dy * (this.zoomSize.height / this.size.height) + Math.min(0, this.zoomSize.height - this.zoomBox.innerHeight) / 2)
        })
      }
      if ("magnifier" == this.zoomBox.mode) {
        return
      }
      this.moveTimer = setTimeout(this.moveBind, 16)
    }, swipe: function () {
      var U, K, P = 30, M = 201, R, S = "", J = {}, I, O, T = 0, V = {transition: A.browser.cssTransform + String.fromCharCode(32) + "300ms cubic-bezier(.18,.35,.58,1)"}, L, Q, N = i(function (W) {
        if (!this.ready || this.zoomBox.active) {
          return
        }
        if (W.state == "dragstart") {
          clearTimeout(this.activateTimer);
          this.activateTimer = null;
          T = 0;
          J = {x: W.x, y: W.y, ts: W.timeStamp};
          U = this.size.width;
          K = U / 2;
          this.image.node.jRemoveEvent("transitionend");
          this.image.node.jSetCssProp("transition", "");
          this.image.node.jSetCssProp("transform", "translate3d(0, 0, 0)");
          Q = null
        } else {
          I = (W.x - J.x);
          O = {x: 0, y: 0, z: 0};
          if (null === Q) {
            Q = (Math.abs(W.x - J.x) < Math.abs(W.y - J.y))
          }
          if (Q) {
            return
          }
          W.stop();
          if ("dragend" == W.state) {
            T = 0;
            L = null;
            R = W.timeStamp - J.ts;
            if (Math.abs(I) > K || (R < M && Math.abs(I) > P)) {
              if ((S = (I > 0) ? "backward" : (I <= 0) ? "forward" : "")) {
                if (S == "backward") {
                  L = this.getPrev();
                  T += U * 10
                } else {
                  L = this.getNext();
                  T -= U * 10
                }
              }
            }
            O.x = T;
            O.deg = -90 * (O.x / U);
            this.image.node.jAddEvent("transitionend", i(function (X) {
              this.image.node.jRemoveEvent("transitionend");
              this.image.node.jSetCssProp("transition", "");
              if (L) {
                this.image.node.jSetCss({transform: "translate3d(" + O.x + "px, 0px, 0px)"});
                this.update(L, true)
              }
            }).jBind(this));
            this.image.node.jSetCss(V);
            this.image.node.jSetCss({"transition-duration": O.x ? "100ms" : "300ms", opacity: 1 - 0.7 * Math.abs(O.x / U), transform: "translate3d(" + O.x + "px, 0px, 0px)"});
            I = 0;
            return
          }
          O.x = I;
          O.z = -50 * Math.abs(O.x / K);
          O.deg = -60 * (O.x / K);
          this.image.node.jSetCss({opacity: 1 - 0.7 * Math.abs(O.x / K), transform: "translate3d(" + O.x + "px, 0px, " + O.z + "px)"})
        }
      }).jBind(this);
      this.node.jAddEvent("touchdrag", N)
    }, setupExpandGallery: function () {
      var J, I;
      if (this.additionalImages.length) {
        this.expandGallery = this.additionalImages
      } else {
        J = this.placeholder.getAttribute("data-gallery");
        if (J) {
          if (A.browser.features.query) {
            I = A.$A(document.querySelectorAll('.MagicZoom[data-gallery="' + J + '"]'))
          } else {
            I = A.$A(document.getElementsByTagName("A")).filter(function (K) {
              return J == K.getAttribute("data-gallery")
            })
          }
          i(I).jEach(function (L) {
            var K, M;
            K = j(L);
            if (K && K.additionalImages.length > 0) {
              return
            }
            if (K) {
              M = new l(K.image.small.url, K.image.zoom.url, K.image.caption, null, K.image.origin)
            } else {
              M = new l().parseNode(L, K ? K.originalTitle : null)
            }
            if (this.image.zoom.src.has(M.zoom.url) && this.image.small.src.has(M.small.url)) {
              M = this.image
            }
            this.expandGallery.push(M)
          }, this);
          this.primaryImage = this.image
        }
      }
      if (this.expandGallery.length > 1) {
        this.expandStage.jAddClass("with-thumbs");
        this.expandNav = A.$new("div", {"class": "mz-expand-thumbnails"}).jAppendTo(this.expandStage);
        this.expandThumbs = new s(this.expandNav);
        i(this.expandGallery).jEach(function (K) {
          var L = i(function (M) {
            this.setActiveThumb(K);
            this.update(K)
          }).jBind(this);
          K.selector = this.expandThumbs.addItem(A.$new("img", {src: K.getURL("small")}).jAddEvent("tap btnclick", function (M) {
            M.stop()
          }).jAddEvent("tap " + ("hover" == this.option("selectorTrigger") ? "mouseover mouseout" : "btnclick"), i(function (N, M) {
            if (this.updateTimer) {
              clearTimeout(this.updateTimer)
            }
            this.updateTimer = false;
            if ("mouseover" == N.type) {
              this.updateTimer = i(L).jDelay(M)
            } else {
              if ("tap" == N.type || "btnclick" == N.type) {
                L()
              }
            }
          }).jBindAsEvent(this, 60)))
        }, this);
        this.buttons.next.show();
        this.buttons.prev.show()
      } else {
        this.expandStage.jRemoveClass("with-thumbs");
        this.buttons.next.hide();
        this.buttons.prev.hide()
      }
    }, destroyExpandGallery: function () {
      var I;
      if (this.expandThumbs) {
        this.expandThumbs.stop();
        this.expandThumbs = null
      }
      if (this.expandNav) {
        this.expandNav.jRemove();
        this.expandNav = null
      }
      if (this.expandGallery.length > 1 && !this.additionalImages.length) {
        this.node.jRemoveEvent("touchdrag");
        this.image.node.jRemove().getAttribute("style");
        this.image.node.removeAttribute("style");
        this.primaryImage.node.jAppendTo(this.node);
        this.setupZoom(this.primaryImage);
        while (I = this.expandGallery.pop()) {
          if (I !== this.primaryImage) {
            if (I.small.node) {
              I.small.node.kill();
              I.small.node = null
            }
            if (I.zoom.node) {
              I.zoom.node.kill();
              I.zoom.node = null
            }
            I = null
          }
        }
      }
      this.expandGallery = []
    }, close: function () {
      if (!this.ready || !this.expanded) {
        return
      }
      if ("ios" == A.browser.platform && "safari" == A.browser.uaName && 7 == parseInt(A.browser.uaVersion)) {
        clearInterval(n);
        n = null
      }
      i(document).jRemoveEvent("keydown", this.keyboardCallback);
      this.deactivate(null, true);
      this.ready = false;
      if (y.browser.fullScreen.capable && y.browser.fullScreen.enabled()) {
        y.browser.fullScreen.cancel()
      } else {
        if (A.browser.features.transition) {
          this.node.jRemoveEvent("transitionend").jSetCss({transition: ""});
          this.node.jAddEvent("transitionend", this.onClose);
          if (A.browser.chrome && ("chrome" === A.browser.uaName || "opera" === A.browser.uaName)) {
            setTimeout(i(function () {
              this.onClose()
            }).jBind(this), 600)
          }
          this.expandBg.jRemoveEvent("transitionend").jSetCss({transition: ""});
          this.expandBg.jSetCss({transition: "all 0.6s cubic-bezier(0.895, 0.030, 0.685, 0.220) 0.0s"}).jGetSize();
          if (A.browser.androidBrowser && "chrome" !== A.browser.uaName) {
            this.node.jSetCss({transition: "all .4s cubic-bezier(0.600, 0, 0.735, 0.045) 0s"}).jGetSize()
          } else {
            this.node.jSetCss({transition: "all .4s cubic-bezier(0.600, -0.280, 0.735, 0.045) 0s"}).jGetSize()
          }
          if (false !== this.zoomBox.mode && "always" == this.option("expandZoomOn") && "magnifier" !== this.option("expandZoomMode")) {
            this.image.node.jSetCss({"max-height": this.image.jGetSize("zoom").height});
            this.image.node.jSetCss({"max-width": this.image.jGetSize("zoom").width})
          }
          this.expandBg.jSetCss({opacity: 0.4});
          this.node.jSetCss({opacity: 0.01, transform: "scale(0.4)"})
        } else {
          this.onClose()
        }
      }
    }, expand: function (K) {
      if (!this.image.loaded() || !this.ready || this.expanded) {
        if (!this.image.loaded()) {
          if (K) {
            this.initEvent = e(K);
            K.stopQueue()
          }
          this.image.load(this.setupZoom.jBind(this));
          if (!this.loadTimer) {
            this.loadTimer = i(this.showLoading).jBind(this).jDelay(400)
          }
        }
        return
      }
      if (K) {
        K.stopQueue()
      }
      var I = i(this.node).jFetch("cr"), J = document.createDocumentFragment();
      this.hideHint();
      this.hintRuns--;
      this.deactivate(null, true);
      this.unregisterActivateEvent();
      this.unregisterDeactivateEvent();
      this.ready = false;
      if (!this.expandBox) {
        this.expandBox = A.$new("div").jAddClass("mz-expand").jAddClass(this.option("cssClass")).jSetCss({opacity: 0});
        this.expandStage = A.$new("div").jAddClass("mz-expand-stage").jAppendTo(this.expandBox);
        this.expandControls = A.$new("div").jAddClass("mz-expand-controls").jAppendTo(this.expandStage);
        i(["prev", "next", "close"]).jEach(function (M) {
          var L = "mz-button";
          this.buttons[M] = A.$new("button", {title: this.option("text-btn-" + M)}).jAddClass(L).jAddClass(L + "-" + M);
          J.appendChild(this.buttons[M]);
          switch (M) {
            case"prev":
              this.buttons[M].jAddEvent("tap btnclick", function (N) {
                N.stop();
                this.update(this.getPrev())
              }.jBindAsEvent(this));
              break;
            case"next":
              this.buttons[M].jAddEvent("tap btnclick", function (N) {
                N.stop();
                this.update(this.getNext())
              }.jBindAsEvent(this));
              break;
            case"close":
              this.buttons[M].jAddEvent("tap btnclick", function (N) {
                N.stop();
                this.close()
              }.jBindAsEvent(this));
              break
          }
        }, this);
        this.expandControls.append(J);
        this.expandBox.jAddEvent("mousescroll touchstart dbltap", i(function (L) {
          i(L).stop()
        }));
        if (this.option("closeOnClickOutside")) {
          this.expandBox.jAddEvent("tap btnclick", function (N) {
            var M = N.jGetPageXY(), L = i("magnifier" === this.option("expandZoomMode") ? this.zoomBox.node : this.zoomBox.image).jGetRect();
            if ("always" !== this.option("expandZoomOn") && L.top <= M.y && M.y <= L.bottom && L.left <= M.x && M.x <= L.right) {
              N.stopQueue();
              this.deactivate(N);
              return
            }
            if ("always" !== this.option("expandZoomOn") && this.node.hasChild(N.getOriginalTarget())) {
              return
            }
            N.stop();
            this.close()
          }.jBindAsEvent(this))
        }
        this.keyboardCallback = i(function (M) {
          var L = null;
          if (27 !== M.keyCode && 37 !== M.keyCode && 39 !== M.keyCode) {
            return
          }
          i(M).stop();
          if (27 === M.keyCode) {
            this.close()
          } else {
            L = (37 === M.keyCode) ? this.getPrev() : this.getNext();
            if (L) {
              this.update(L)
            }
          }
        }).jBindAsEvent(this);
        this.onExpand = i(function () {
          var L;
          this.node.jRemoveEvent("transitionend").jSetCss({transition: "", transform: "translate3d(0,0,0)"});
          if (this.expanded) {
            return
          }
          this.expanded = true;
          this.expandBox.jRemoveClass("mz-expand-opening").jSetCss({opacity: 1});
          this.zoomBox.setMode(this.option("expandZoomMode"));
          this.zoomSize = A.detach(this.zoomSizeOrigin);
          this.resizeCallback();
          if (this.expandCaption && this.image.caption) {
            if (this.image.link) {
              this.expandCaption.append(A.$new("a", {href: this.image.link}).jAddEvent("tap btnclick", this.openLink.jBind(this)).changeContent(this.image.caption))
            } else {
              this.expandCaption.changeContent(this.image.caption)
            }
            this.expandCaption.jAddClass("mz-show")
          }
          if ("always" !== this.option("expandZoomOn")) {
            this.registerActivateEvent(true);
            this.registerDeactivateEvent(true)
          }
          this.ready = true;
          if ("always" === this.option("expandZoomOn")) {
            if (false !== this.zoomBox.mode) {
              this.zoomBox.enable(true)
            }
            if (A.browser.mobile && this.mobileZoomHint) {
              this.mobileZoomHint = false
            }
            this.activate()
          }
          if ((A.browser.mobile || this.option("forceTouch")) && this.zoomBox.enabled) {
            if (this.mobileZoomHint || this.hintRuns > 0) {
              this.showHint(true, this.option("textClickZoomHint"))
            }
            this.mobileZoomHint = false
          }
          this.expandControls.jRemoveClass("mz-hidden").jAddClass("mz-fade mz-visible");
          this.expandNav && this.expandNav.jRemoveClass("mz-hidden").jAddClass("mz-fade mz-visible");
          if (this.expandThumbs) {
            this.expandThumbs.run();
            this.setActiveThumb(this.image)
          }
          if (I) {
            I.jAppendTo(this.expandBox, ((Math.floor(Math.random() * 101) + 1) % 2) ? "top" : "bottom")
          }
          if (this.expandGallery.length && !this.additionalImages.length) {
            this.swipe()
          }
          i(document).jAddEvent("keydown", this.keyboardCallback);
          if ("ios" == A.browser.platform && "safari" == A.browser.uaName && 7 == parseInt(A.browser.uaVersion)) {
            n = w()
          }
          u("onExpandOpen", this.id)
        }).jBind(this);
        this.onClose = i(function () {
          this.node.jRemoveEvent("transitionend");
          if (!this.expanded) {
            return
          }
          if (this.expanded) {
            i(document).jRemoveEvent("keydown", this.keyboardCallback);
            this.deactivate(null, true)
          }
          this.destroyExpandGallery();
          this.expanded = false;
          this.zoomBox.setMode(this.option("zoomMode"));
          this.node.replaceChild(this.image.getNode("small"), this.image.node);
          this.image.setCurNode("small");
          i(this.image.node).jSetCss({
            width: "",
            height: "",
            "max-width": Math.min(this.image.jGetSize("small").width),
            "max-height": Math.min(this.image.jGetSize("small").height)
          });
          this.node.jSetCss({opacity: "", transition: ""});
          this.node.jSetCss({transform: "translate3d(0,0,0)"});
          i(this.placeholder).replaceChild(this.node, this.stubNode);
          this.setSize(true);
          if (this.expandCaption) {
            this.expandCaption.jRemove();
            this.expandCaption = null
          }
          this.unregisterActivateEvent();
          this.unregisterDeactivateEvent();
          if ("always" == this.option("zoomOn")) {
            this.activate()
          } else {
            if (false !== this.option("zoomMode")) {
              this.registerActivateEvent("click" === this.option("zoomOn"));
              this.registerDeactivateEvent("click" === this.option("zoomOn") && !this.option("expand"))
            }
          }
          this.showHint();
          this.expandBg.jRemoveEvent("transitionend");
          this.expandBox.jRemove();
          this.expandBg.jRemove();
          this.expandBg = null;
          i(A.browser.getDoc()).jRemoveClass("mz-expanded-view-open");
          this.ready = true;
          if (A.browser.ieMode < 10) {
            this.resizeCallback()
          } else {
            i(window).jRaiseEvent("UIEvent", "resize")
          }
          u("onExpandClose", this.id)
        }).jBind(this);
        this.expandImageStage = A.$new("div", {"class": "mz-image-stage"}).jAppendTo(this.expandStage);
        this.expandFigure = A.$new("figure").jAppendTo(this.expandImageStage);
        this.stubNode = this.node.cloneNode(false)
      }
      this.setupExpandGallery();
      i(A.browser.getDoc()).jAddClass("mz-expanded-view-open");
      i(document.body).jGetSize();
      if ("fullscreen" == this.option("expand")) {
        this.prepareExpandedView();
        y.browser.fullScreen.request(this.expandBox, {
          onEnter: i(function () {
            this.onExpand()
          }).jBind(this), onExit: this.onClose, fallback: i(function () {
            this.expandToWindow()
          }).jBind(this)
        })
      } else {
        setTimeout(i(function () {
          this.prepareExpandedView();
          this.expandToWindow()
        }).jBind(this), 96)
      }
    }, prepareExpandedView: function () {
      var J, I;
      J = A.$new("img", {src: this.image.getURL("zoom")});
      this.expandBg = A.$new("div").jAddClass("mz-expand-bg").append((A.browser.features.cssFilters || A.browser.ieMode < 10) ? J : new A.SVGImage(J).blur(b).getNode()).jAppendTo(this.expandBox);
      if ("always" === this.option("expandZoomOn") && false !== this.option("expandZoomMode")) {
        this.expandStage.jAddClass("mz-always-zoom" + ("zoom" === this.option("expandZoomMode") ? " mz-zoom-in" : "")).jGetSize()
      }
      I = i(this.node)[(A.browser.ieMode < 10) ? "jGetSize" : "getBoundingClientRect"]();
      i(this.stubNode).jSetCss({width: I.width, height: I.height});
      this.node.replaceChild(this.image.getNode("zoom"), this.image.node);
      this.image.setCurNode("zoom");
      this.expandBox.jAppendTo(document.body);
      this.expandMaxWidth = function () {
        var K = this.expandImageStage;
        if (i(this.expandFigure).jGetSize().width > 50) {
          K = this.expandFigure
        }
        return function () {
          return "always" == this.option("expandZoomOn") && false !== this.option("expandZoomMode") && "magnifier" !== this.option("expandZoomMode") ? Infinity : Math.round(i(K).getInnerSize().width)
        }
      }.call(this);
      this.expandMaxHeight = function () {
        var K = this.expandImageStage;
        if (i(this.expandFigure).jGetSize().height > 50) {
          K = this.expandFigure
        }
        return function () {
          return "always" == this.option("expandZoomOn") && false !== this.option("expandZoomMode") && "magnifier" !== this.option("expandZoomMode") ? Infinity : Math.round(i(K).getInnerSize().height)
        }
      }.call(this);
      this.expandControls.jRemoveClass("mz-fade mz-visible").jAddClass("mz-hidden");
      this.expandNav && this.expandNav.jRemoveClass("mz-fade mz-visible").jAddClass("mz-hidden");
      this.image.node.jSetCss({"max-height": Math.min(this.image.jGetSize("zoom").height, this.expandMaxHeight())});
      this.image.node.jSetCss({"max-width": Math.min(this.image.jGetSize("zoom").width, this.expandMaxWidth())});
      this.expandFigure.append(i(this.placeholder).replaceChild(this.stubNode, this.node));
      if (this.option("expandCaption")) {
        this.expandCaption = A.$new("figcaption", {"class": "mz-caption"}).jAppendTo(this.expandFigure)
      }
    }, expandToWindow: function () {
      this.node.jSetCss({transition: ""});
      this.node.jSetCss({transform: "scale(0.6)"}).jGetSize();
      if (A.browser.androidBrowser && "chrome" !== A.browser.uaName) {
        this.node.jSetCss({transition: A.browser.cssTransform + " 0.6s cubic-bezier(0.175, 0.885, 0.320, 1) 0s"})
      } else {
        this.node.jSetCss({transition: A.browser.cssTransform + " 0.6s cubic-bezier(0.175, 0.885, 0.320, 1.275) 0s"})
      }
      if (A.browser.features.transition) {
        this.node.jAddEvent("transitionend", this.onExpand);
        if (A.browser.chrome && ("chrome" === A.browser.uaName || "opera" === A.browser.uaName)) {
          setTimeout(i(function () {
            this.onExpand()
          }).jBind(this), 800)
        }
      } else {
        this.onExpand.jDelay(16, this)
      }
      this.expandBox.jSetCss({opacity: 1});
      this.node.jSetCss({transform: "scale(1)"})
    }, openLink: function () {
      if (this.image.link) {
        window.open(this.image.link, "_self")
      }
    }, getNext: function () {
      var I = (this.expanded ? this.expandGallery : this.additionalImages).filter(function (L) {
        return (-1 !== L.small.state || -1 !== L.zoom.state)
      }), J = I.length, K = i(I).indexOf(this.image) + 1;
      return (1 >= J) ? null : I[(K >= J) ? 0 : K]
    }, getPrev: function () {
      var I = (this.expanded ? this.expandGallery : this.additionalImages).filter(function (L) {
        return (-1 !== L.small.state || -1 !== L.zoom.state)
      }), J = I.length, K = i(I).indexOf(this.image) - 1;
      return (1 >= J) ? null : I[(K < 0) ? J - 1 : K]
    }, imageByURL: function (J, K) {
      var I = this.additionalImages.filter(function (L) {
          return ((L.zoom.src.has(J) || L.zoom.url.has(J)) && (L.small.src.has(K) || L.small.url.has(K)))
        }) || [];
      return I[0] || ((K && J && "string" === A.jTypeOf(K) && "string" === A.jTypeOf(J)) ? new l(K, J) : null)
    }, imageByOrigin: function (J) {
      var I = this.additionalImages.filter(function (K) {
          return (K.origin === J)
        }) || [];
      return I[0]
    }, imageByIndex: function (I) {
      return this.additionalImages[I]
    }
  };
  v = {
    version: "v5.1.12 (Plus) DEMO", start: function (L, J) {
      var K = null, I = [];
      A.$A((L ? [i(L)] : A.$A(document.byClass("MagicZoom")).concat(A.$A(document.byClass("MagicZoomPlus"))))).jEach((function (M) {
        if (i(M)) {
          if (!j(M)) {
            K = new k(M, J);
            if (z && !K.option("autostart")) {
              K.stop();
              K = null
            } else {
              F.push(K);
              I.push(K)
            }
          }
        }
      }).jBind(this));
      return L ? I[0] : I
    }, stop: function (L) {
      var J, K, I;
      if (L) {
        (K = j(L)) && (K = F.splice(F.indexOf(K), 1)) && K[0].stop() && (delete K[0]);
        return
      }
      while (J = F.length) {
        K = F.splice(J - 1, 1);
        K[0].stop();
        delete K[0]
      }
    }, refresh: function (I) {
      this.stop(I);
      return this.start(I)
    }, update: function (N, M, L, J) {
      var K = j(N), I;
      if (K) {
        I = "element" === A.jTypeOf(M) ? K.imageByOrigin(M) : K.imageByURL(M, L);
        if (I) {
          K.update(I)
        }
      }
    }, switchTo: function (L, K) {
      var J = j(L), I;
      if (J) {
        switch (A.jTypeOf(K)) {
          case"element":
            I = J.imageByOrigin(K);
            break;
          case"number":
            I = J.imageByIndex(K);
            break;
          default:
        }
        if (I) {
          J.update(I)
        }
      }
    }, prev: function (J) {
      var I;
      (I = j(J)) && I.update(I.getPrev())
    }, next: function (J) {
      var I;
      (I = j(J)) && I.update(I.getNext())
    }, zoomIn: function (J) {
      var I;
      (I = j(J)) && I.activate()
    }, zoomOut: function (J) {
      var I;
      (I = j(J)) && I.deactivate()
    }, expand: function (J) {
      var I;
      (I = j(J)) && I.expand()
    }, close: function (J) {
      var I;
      (I = j(J)) && I.close()
    }, registerCallback: function (I, J) {
      if (!q[I]) {
        q[I] = []
      }
      if ("function" == A.jTypeOf(J)) {
        q[I].push(J)
      }
    }, running: function (I) {
      return !!j(I)
    }
  };
  i(document).jAddEvent("domready", function () {
    var J = window[D + "Options"] || {};
    t = t();
    d();
    H = A.$new("div", {"class": "magic-hidden-wrapper"}).jAppendTo(document.body);
    G = (A.browser.mobile && window.matchMedia && window.matchMedia("(max-device-width: 767px), (max-device-height: 767px)").matches);
    if (A.browser.mobile) {
      A.extend(p, m)
    }
    for (var I = 0; I < B.length; I++) {
      if (J[B[I]] && A.$F !== J[B[I]]) {
        v.registerCallback(B[I], J[B[I]])
      }
    }
    v.start();
    z = false
  });
  window.MagicZoomPlus = window.MagicZoomPlus || {};
  return v
})();