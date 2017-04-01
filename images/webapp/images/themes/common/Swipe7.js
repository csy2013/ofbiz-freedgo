function Swipe7(v, f) {
  function d(a) {
    0 == a ? b.style.left = -100 * (n - 2) + "%" : a == n - 1 && (b.style.left = "-100%")
  }

  function w(a, c) {
    clearTimeout(C);
    clearTimeout(A);
    clearTimeout(x);
    var m;
    k = p.width();
    var e = parseInt(parseInt(b.style.left, 10) / -100, 10), g = D(b);
    if (!c || 0 === g) {
      1 == a ? (g = parseInt((g - k) / k, 10) * k, m = e + 1) : 0 < g ? (g = parseInt((g + k) / k, 10) * k, m = e - 1) : (g = 0, m = e);
      var h = c ? 1 : Math.abs(g) / k * 0.4;
      B(h);
      y(g);
      C = setTimeout(function () {
        l(m);
        A = setTimeout(function () {
          b.style.WebkitTransition = "";
          b.style.transition = "";
          b.style.left = -100 * m + "%";
          y(0);
          d(m);
          f.auto && (x = setTimeout(function () {
            w(1, !0)
          }, 2E3))
        }, 1E3 * h)
      }, 0)
    }
  }

  function l(a) {
    a = a == n - 1 ? 0 : 0 == a ? q - 1 : a - 1;
    $(F.removeClass("current")[a]).addClass("current");
    f.onStep && f.onStep(a + 1)
  }

  function B(a) {
    b.style.WebkitTransition = "-webkit-transform " + a + "s ease-out";
    b.style.transition = "transform " + a + "s ease-out"
  }

  function y(a) {
    b.style.WebkitTransform = "translate3d(" + a + "px, 0, 0)";
    b.style.transform = "translate3d(" + a + "px, 0, 0)"
  }

  function E(a) {
    a = a.touches[0];
    return [a.clientX, a.clientY]
  }

  function D(a) {
    a = getComputedStyle(a,
      null)[Swipe7.transform].split(")")[0].split(", ");
    (a = +(a[12] || a[4])) || (a = 0);
    return a
  }

  f || (f = {});
  var p = $(v);
  v = p[0];
  var r = p.find(".swipe7list"), b = r[0], c = r.find(".swipe7item"), q = c.length;
  if (!(1 >= q)) {
    c.each(function (a, b) {
      $(b).attr("correct-index", a + 1)
    });
    var e = c[0], h = e.cloneNode(!0), c = c[q - 1].cloneNode(!0);
    $(c).attr("correct-index", 0);
    $(h).attr("correct-index", q + 1);
    b.appendChild(h);
    b.insertBefore(c, e);
    var c = r.find(".swipe7item"), n = c.length;
    r.css({width: 100 * n + "%", left: "-100%"});
    c.css("width", 1 / n * 100 + "%");
    e = document.createElement("div");
    e.className = "swipe7Indicator";
    for (h = 0; h < q; h++)c = document.createElement("span"), c.className = "swipe7IndicatorItem " + (0 == h ? "current" : ""), e.appendChild(c);
    p.append(e);
    var F = $("span", e), z, s, t = 0, u, k, A, C, x;
    r.bind("touchstart", function (a) {
      clearTimeout(A);
      clearTimeout(x);
      u = 0;
      var c = parseInt(parseInt(b.style.left, 10) / -100, 10);
      t = D(b);
      b.style.WebkitTransition = "";
      b.style.transition = "";
      s = z = E(a);
      var e = p.width();
      a = $(a.target).closest("li").attr("correct-index");
      a != c && (b.style.left =
        -100 * a + "%", t += (a - c) * e);
      y(t);
      d(a)
    }).bind("touchmove", function (a) {
      s = E(a);
      var b = Math.abs(s[0] - z[0]), c = Math.abs(s[1] - z[1]);
      b > c && (a.stopPropagation(), a.preventDefault());
      u = s[0] - z[0];
      y(t + u)
    }).bind("touchend", function (a) {
      0 < u ? w(-1) : 0 > u && w(1)
    });
    f.auto && (x = setTimeout(function () {
      w(1, !0)
    }, 2E3))
  }
}
(function () {
  var v = document.createElement("div").style,
    f = function () {
      for (var d = ["t", "webkitT", "MozT", "msT", "OT"], f, l = 0, B = d.length; l < B; l++)
        if (f = d[l] + "ransform", f in v)return d[l].substr(0, d[l].length - 1);
      return !1
    }();
  Swipe7.transform = function (d) {
    if ("" === f)return d;
    d = d.charAt(0).toUpperCase() + d.substr(1);
    return f + d
  }("transform")
})();
