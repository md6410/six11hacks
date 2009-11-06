%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%
%% Merged from now-journal.cls and now-book.cls (v2.1)
%% by Suresh Kumar and Johny Sebastian based on book.cls
%% 
%% This is $Id: now.cls,v 1.2 2005/04/19 13:28:49 taco Exp $
%%
%%
%% A LaTeX class file for NOW publishing house
%%
%% Changes (c) 2005 Taco Hoekwater, Elvenkind B.V.
%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 
%% 
%% \CharacterTable
%%  {Upper-case    \A\B\C\D\E\F\G\H\I\J\K\L\M\N\O\P\Q\R\S\T\U\V\W\X\Y\Z
%%   Lower-case    \a\b\c\d\e\f\g\h\i\j\k\l\m\n\o\p\q\r\s\t\u\v\w\x\y\z
%%   Digits        \0\1\2\3\4\5\6\7\8\9
%%   Exclamation   \!     Double quote  \"     Hash (number) \#
%%   Dollar        \$     Percent       \%     Ampersand     \&
%%   Acute accent  \'     Left paren    \(     Right paren   \)
%%   Asterisk      \*     Plus          \+     Comma         \,
%%   Minus         \-     Point         \.     Solidus       \/
%%   Colon         \:     Semicolon     \;     Less than     \<
%%   Equals        \=     Greater than  \>     Question mark \?
%%   Commercial at \@     Left bracket  \[     Backslash     \\
%%   Right bracket \]     Circumflex    \^     Underscore    \_
%%   Grave accent  \`     Left brace    \{     Vertical bar  \|
%%   Right brace   \}     Tilde         \~}
\NeedsTeXFormat{LaTeX2e}[1995/12/01]
\ProvidesClass{now}
              [2005/04/08 v2.2 Now LaTeX document class]
\newcommand\@ptsize{2}
\newif\if@restonecol
\newif\if@titlepage
\@titlepagetrue
\newif\if@openright
\newif\if@mainmatter \@mainmattertrue
\newif\ifnowbook
\newif\ifnowpdf
\if@compatibility\else
\DeclareOption{a4paper}
   {\setlength\paperheight {297mm}%
    \setlength\paperwidth  {210mm}}
\DeclareOption{a5paper}
   {\setlength\paperheight {210mm}%
    \setlength\paperwidth  {148mm}}
\DeclareOption{b5paper}
   {\setlength\paperheight {250mm}%
    \setlength\paperwidth  {176mm}}
%%\DeclareOption{letterpaper} -ettt - oud
%%   {\setlength\paperheight {9in} -ettt - oud
%%    \setlength\paperwidth  {6in}} -ettt - oud
\DeclareOption{letterpaper}
   {\setlength\paperheight {11in}
    \setlength\paperwidth  {8.5in}}
\DeclareOption{legalpaper}
   {\setlength\paperheight {14in}%
    \setlength\paperwidth  {8.5in}}
\DeclareOption{executivepaper}
   {\setlength\paperheight {10.5in}%
    \setlength\paperwidth  {7.25in}}
\DeclareOption{landscape}
   {\setlength\@tempdima   {\paperheight}%
    \setlength\paperheight {\paperwidth}%
    \setlength\paperwidth  {\@tempdima}}
\fi
\DeclareOption{10pt}{\renewcommand\@ptsize{0}}
\DeclareOption{11pt}{\renewcommand\@ptsize{1}}
\DeclareOption{12pt}{\renewcommand\@ptsize{2}}
\DeclareOption{oneside}{\@twosidefalse \@mparswitchfalse}
\DeclareOption{twoside}{\@twosidetrue  \@mparswitchfalse}%true
\DeclareOption{draft}{\setlength\overfullrule{5pt}}
\DeclareOption{final}{\setlength\overfullrule{0pt}}
\DeclareOption{titlepage}{\@titlepagetrue}
\DeclareOption{leqno}{\input{leqno.clo}}
\DeclareOption{fleqn}{\input{fleqn.clo}}
\DeclareOption{openbib}{%
  \AtEndOfPackage{%
   \renewcommand\@openbib@code{%
      \advance\leftmargin\bibindent
      \itemindent -\bibindent
      \listparindent \itemindent
      \parsep \z@
      }%
   \renewcommand\newblock{\par}}%
}
\newif\ifmodeset
\DeclareOption{printed}{\nowbooktrue  \nowpdffalse \modesettrue}
\DeclareOption{book}   {\nowbooktrue  \nowpdffalse \modesettrue}
\DeclareOption{ebook}  {\nowbooktrue  \nowpdftrue  \modesettrue}
\DeclareOption{journal}{\nowbookfalse \nowpdffalse \modesettrue}

\ExecuteOptions{letterpaper,twoside,onecolumn,final,openright,11pt}
\ProcessOptions

\def\testprintedmode{printed}
\def\testbookmode   {book}
\def\testebookmode  {ebook}
\def\testjournalmode{journal}

\ifmodeset \else
  \expandafter \ifx \csname mode\endcsname \relax
  \else
     \ifx\testprintedmode \mode
         \nowbooktrue  \nowpdffalse 
     \else \ifx\testbookmode \mode
         \nowbooktrue  \nowpdffalse 
     \else \ifx\testebookmode \mode
         \nowbooktrue  \nowpdftrue
     \else \ifx\testjournalmode \mode
         \nowbookfalse \nowpdffalse
     \fi \fi \fi \fi
  \fi
\fi



\newif\ifinpdfmode

\expandafter\ifx\csname pdfoutput\endcsname\relax 
  \inpdfmodefalse
\else 
  \ifnowpdf
    \pdfoutput=1
  \fi
  \ifnum\pdfoutput=1
    \inpdfmodetrue
  \fi
\fi

\def\@xvpt{15}
\newdimen\footheight
\footheight=7pt % For checking SHK
%
\input{now1\@ptsize.clo}
%
%% from enumerate.sty
\newtoks\@enLab
\def\@enQmark{?}
\def\@enLabel#1#2{%
  \edef\@enThe{\noexpand#1{\@enumctr}}%
  \@enLab\expandafter{\the\@enLab\csname the\@enumctr\endcsname}%
  \@enloop}
\def\@enSpace{\afterassignment\@enSp@ce\let\@tempa= }
\def\@enSp@ce{\@enLab\expandafter{\the\@enLab\space}\@enloop}
\def\@enGroup#1{\@enLab\expandafter{\the\@enLab{#1}}\@enloop}
\def\@enOther#1{\@enLab\expandafter{\the\@enLab#1}\@enloop}
\def\@enloop{\futurelet\@entemp\@enloop@}
\def\@enloop@{%
  \ifx A\@entemp         \def\@tempa{\@enLabel\Alph  }\else
  \ifx a\@entemp         \def\@tempa{\@enLabel\alph  }\else
  \ifx i\@entemp         \def\@tempa{\@enLabel\roman }\else
  \ifx I\@entemp         \def\@tempa{\@enLabel\Roman }\else
  \ifx 1\@entemp         \def\@tempa{\@enLabel\arabic}\else
  \ifx \@sptoken\@entemp \let\@tempa\@enSpace         \else
  \ifx \bgroup\@entemp   \let\@tempa\@enGroup         \else
  \ifx \@enum@\@entemp   \let\@tempa\@gobble          \else
                         \let\@tempa\@enOther
             \fi\fi\fi\fi\fi\fi\fi\fi
  \@tempa}
\def\enumerate{%
  \ifnum \@enumdepth >3 \@toodeep\else
      \advance\@enumdepth \@ne
      \edef\@enumctr{enum\romannumeral\the\@enumdepth}\fi
  \@ifnextchar[{\@@enum@}{\@enum@}}
\def\@@enum@[#1]{%
  \@enLab{}\let\@enThe\@enQmark
  \@enloop#1\@enum@
  \ifx\@enThe\@enQmark\@warning{The counter will not be printed.%
   ^^J\space\@spaces\@spaces\@spaces The label is: \the\@enLab}\fi
  \expandafter\edef\csname label\@enumctr\endcsname{\the\@enLab}%
  \expandafter\let\csname the\@enumctr\endcsname\@enThe
  \csname c@\@enumctr\endcsname7
  \expandafter\settowidth
            \csname leftmargin\romannumeral\@enumdepth\endcsname
            {\hskip2pc\the\@enLab\hspace{\labelsep}}%
  \@enum@}
\def\@enum@{\list{\csname label\@enumctr\endcsname}%
           {\usecounter{\@enumctr}\rightmargin1pc\def\makelabel##1{\hss\llap{##1}}}}

\def\itemize{%
  \ifnum \@itemdepth >\thr@@\@toodeep\else
    \advance\@itemdepth\@ne
    \edef\@itemitem{labelitem\romannumeral\the\@itemdepth}%
    \expandafter
    \list
      \csname\@itemitem\endcsname
      {\def\makelabel##1{\hss\llap{##1}}\rightmargin1pc}
  \fi}
\let\enditemize =\endlist


\setlength\lineskip{1\p@}
\setlength\normallineskip{1\p@}
\renewcommand\baselinestretch{}
\setlength\parskip{0pt}%\p@ \@plus \p@}
\@lowpenalty   51
\@medpenalty  151
\@highpenalty 301
\setcounter{topnumber}{2}
\renewcommand\topfraction{0.9}
\setcounter{bottomnumber}{2}
\renewcommand\bottomfraction{0.5}
\setcounter{totalnumber}{3}
\renewcommand\textfraction{0.05}
\renewcommand\floatpagefraction{0.95}
\setcounter{dbltopnumber}{2}
\renewcommand\dbltopfraction{0.9}
\renewcommand\dblfloatpagefraction{0.8}
\def\rhheadfont{\normalfont\small\slshape}
\def\rhfootfont{\normalfont\small}


\if@twoside
  \def\ps@headings{%
      \let\@oddfoot\@empty\let\@evenfoot\@empty
      \def\@evenhead{\thepage\hskip1em\rhheadfont\leftmark\hfil}%
      \def\@oddhead{\hfil{\rhheadfont\rightmark}\hskip1em\thepage}%
      \let\@mkboth\markboth
    \def\chaptermark##1{%
      \markboth{{##1}}{}}%
    \def\sectionmark##1{%
      \markright{{
        \ifnum \c@secnumdepth >\z@
          \thesection. \ %
        \fi##1}}}}
\else
  \def\ps@headings{%
    \let\@oddfoot\@empty
    \def\@oddhead{{\slshape\rightmark}\hfil\thepage}%
    \let\@mkboth\markboth
    \def\chaptermark##1{%
      \markright {{##1}}}}
\fi

\def\ps@plain{\let\@mkboth\@gobbletwo
     \let\@oddhead\@empty\def\@oddfoot{\reset@font\hfil\rhfootfont\thepage
     \hfil}\let\@evenhead\@empty\let\@evenfoot\@oddfoot}










\def\ps@empty{\let\@mkboth\@gobbletwo
     \let\@oddhead\@empty\let\@oddfoot\@empty
     %\reset@font\hfil\smash{\vbox to\z@{\vspace{4pc}\hbox to\hsize{\hfil\rhfootfont\thepage\hfil}}}\hfil} 
     \let\@evenhead\@empty\let\@evenfoot\@oddfoot}

\def\ps@myheadings{%
    \let\@oddfoot\@empty\let\@evenfoot\@empty
    \def\@evenhead{\rhheadfont\thepage\hfil\leftmark}%
    \def\@oddhead{\rhheadfont\rightmark\hfil\thepage}%
    \let\@mkboth\@gobbletwo
    \let\chaptermark\@gobble
    \let\sectionmark\@gobble
}

\newcommand*\chaptermark[1]{}

%\setcounter{secnumdepth}{2} %Modified on 12-Feb-04 Samir
\setcounter{secnumdepth}{3}
\newcounter {part}
\newcounter {chapter}
\newcounter {section}[chapter]
\newcounter {subsection}[section]
\newcounter {subsubsection}[subsection]
\newcounter {paragraph}[subsubsection]
\newcounter {subparagraph}[paragraph]
\renewcommand \thepart {\@Roman\c@part}
\renewcommand \thechapter {\@arabic\c@chapter}
\renewcommand \thesection {\thechapter.\@arabic\c@section}
\renewcommand\thesubsection   {\thesection.\@arabic\c@subsection}
\renewcommand\thesubsubsection{\thesubsection .\@arabic\c@subsubsection}
\renewcommand\theparagraph    {\thesubsubsection.\@arabic\c@paragraph}
\renewcommand\thesubparagraph {\theparagraph.\@arabic\c@subparagraph}
%\newcommand\@chapapp{\chaptername~\thechapter}
\newcommand\frontmatter{%
  \ifnowbook \cleardoublepage \fi % - ettt for journal
  \@mainmatterfalse
  \pagenumbering{roman}} %% - ettt changed roman to arabic

\newcommand\mainmatter{%
  \ifnowbook  \cleardoublepage \fi % - ettt for journal
  \@mainmattertrue
  \pagenumbering{arabic}}

\newcommand\backmatter{%
  \if@openright
    \ifnowbook \cleardoublepage \else \clearpage \fi
  \else
    \clearpage
  \fi
  \@mainmatterfalse}

\newcommand\part{%
  \if@openright
    \ifnowbook \cleardoublepage \else \clearpage \fi
  \else
    \clearpage
  \fi
  \thispagestyle{plain}%
  \if@twocolumn
    \onecolumn
    \@tempswatrue
  \else
    \@tempswafalse
  \fi
  \null\vfil
  \secdef\@part\@spart}

\def\@part[#1]#2{%
    \ifnum \c@secnumdepth >-2\relax
      \refstepcounter{part}%
      \addcontentsline{toc}{part}{\thepart\hspace{1em}#1}%
    \else
      \addcontentsline{toc}{part}{#1}%
    \fi
    \markboth{}{}%
    {\centering
     \interlinepenalty \@M
     \normalfont
     \ifnum \c@secnumdepth >-2\relax
       \huge\bfseries \partname\nobreakspace\thepart
       \par
       \vskip 20\p@
     \fi
     \Huge \bfseries #2\par}%
    \@endpart}
\def\@spart#1{%
    {\centering
     \interlinepenalty \@M
     \normalfont
     \Huge \bfseries #1\par}%
    \@endpart}
\def\@endpart{\vfil\newpage
              \if@twoside
               \if@openright
                \null
                \thispagestyle{empty}%
                \newpage
               \fi
              \fi
              \if@tempswa
                \twocolumn
              \fi}
%\newcommand\chapter{%\if@openright\cleardoublepage\else\clearpage\fi
\newcommand\chapter{%\if@openright\clearpage\else\clearpage\fi
    \ifnowbook \cleardoublepage \else \clearpage \fi %
    \thispagestyle{plain}% 
    \global\@topnum\z@
    \@afterindentfalse
    \secdef\@chapter\@schapter}

\def\@chapter[#1]#2{\ifnum \c@secnumdepth >\m@ne
                       \if@mainmatter
                         \refstepcounter{chapter}%
%                         \typeout{\@chapapp\space\thechapter.}%
                         \addcontentsline{toc}{chapter}%
                                   {\protect\numberline{\thechapter}#1}%
                       \else
                                    \addcontentsline{toc}{chapter}{#1}%
                       \fi
                    \else
                      \addcontentsline{toc}{chapter}{#1}%
                    \fi
                    \chaptermark{#1}%
                    \addtocontents{lof}{\protect\addvspace{10\p@}}%
                    \addtocontents{lot}{\protect\addvspace{10\p@}}%
                    \if@twocolumn
                      \@topnewpage[\@makechapterhead{#2}]%
                    \else
                      \@makechapterhead{#2}%
                      \@afterheading
                    \fi}
\def\CNfont{\sffamily\Huge\bfseries\centering}%
\def\CTfont{\sffamily\LARGE\bfseries\centering\boldmath}%
\def\@makechapterhead#1{\vbox to 19.45pc{%
  \vspace*{15\p@}%
  {\parindent \z@ \raggedright \normalfont
    \ifnum \c@secnumdepth >\m@ne
      \if@mainmatter
        {\CNfont\thechapter\par}\addvspace{10pt}\nobreak
        \noindent\hrule\@width1\columnwidth\@height1\p@
        \vskip 14\p@
      \fi
    \fi
    \interlinepenalty\@M
    {\CTfont#1\vphantom{y}\par}\addvspace{8pt}\nobreak
    \noindent\hrule\@width1\columnwidth\@height1\p@
    \vskip 14pc
  }}}
\def\@schapter#1{\if@twocolumn
                   \@topnewpage[\@makeschapterhead{#1}]%
                 \else
                   \@makeschapterhead{#1}%
                   \@afterheading
                 \fi}
\def\@makeschapterhead#1{%
\vbox to 19.45pc{%
  \vspace*{55\p@}%
  {\parindent \z@ \raggedright \normalfont
    \interlinepenalty\@M
    {\CTfont#1\vphantom{y}\par}\addvspace{8pt}\nobreak
    \noindent\hrule\@width1\columnwidth\@height1\p@
%    \vskip 14pc
  }}}


%%%%%%%%\newbox\abstractbox

\newcommand\abstract[1]{\gdef\@abstract{
     \par\section*{\abstractname}%
          \noindent \ignorespaces%
    #1 \par}} %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%\newenvironment{abstract}
%     {\global\setbox\abstractbox = \vbox\bgroup 
%          \par\section*{\abstractname}%
%          \noindent \ignorespaces}%
%     {\par\egroup}

\newenvironment{preface}{\section*{Preface}}{}
\newenvironment{historicalnotes}{\section*{Historical notes}}{}

\def\sectionfont{\fontsize{12}{14}\selectfont\sffamily\bfseries\boldmath}
%
\newcommand\section{\@startsection {section}{1}{\z@}%
                                   {-15pt \@plus -1pt \@minus -.2pt}%
                                   {7.5pt}%
                                   {\sectionfont}}
\newcommand\subsection{\@startsection{subsection}{2}{\z@}%
                                     {-3.25ex\@plus -1ex \@minus -.2ex}%
                                     {1.5ex \@plus .2ex}%
                                     {\normalfont\large\bfseries}}
\newcommand\subsubsection{\@startsection{subsubsection}{3}{\z@}%
                                     {-3.25ex\@plus -1ex \@minus -.2ex}%
                                     {1.5ex \@plus .2ex}%
                                     {\normalfont\normalsize\bfseries}}
\newcommand\paragraph{\@startsection{paragraph}{4}{\z@}%
                                    {3.25ex \@plus1ex \@minus.2ex}%
                                    {-1em}%
                                    {\normalfont\normalsize\bfseries}}
\newcommand\subparagraph{\@startsection{subparagraph}{5}{\parindent}%
                                       {3.25ex \@plus1ex \@minus .2ex}%
                                       {-1em}%
                                      {\normalfont\normalsize\bfseries}}
\if@twocolumn
  \setlength\leftmargini  {2em}
\else
  \setlength\leftmargini  {36.5pt}
\fi
\leftmargin  \leftmargini
\setlength\leftmarginii  {2.2em}
\setlength\leftmarginiii {1.87em}
\setlength\leftmarginiv  {1.7em}
\if@twocolumn
  \setlength\leftmarginv  {.5em}
  \setlength\leftmarginvi {.5em}
\else
  \setlength\leftmarginv  {1em}
  \setlength\leftmarginvi {1em}
\fi
\setlength  \labelsep  {.5em}
\setlength  \labelwidth{\leftmargini}
\addtolength\labelwidth{-\labelsep}
\@beginparpenalty -\@lowpenalty
\@endparpenalty   -\@lowpenalty
\@itempenalty     -\@lowpenalty
\renewcommand\theenumi{\@arabic\c@enumi}
\renewcommand\theenumii{\@alph\c@enumii}
\renewcommand\theenumiii{\@roman\c@enumiii}
\renewcommand\theenumiv{\@Alph\c@enumiv}
\newcommand\labelenumi{(\theenumi)}
\newcommand\labelenumii{(\theenumii)}
\newcommand\labelenumiii{\theenumiii.}
\newcommand\labelenumiv{\theenumiv.}
\renewcommand\p@enumii{\theenumi}
\renewcommand\p@enumiii{\theenumi(\theenumii)}
\renewcommand\p@enumiv{\p@enumiii\theenumiii}
\newcommand\labelitemi{\raise1pt\hbox{\textbullet}}
\newcommand\labelitemii{\normalfont\bfseries \textendash}
\newcommand\labelitemiii{\textasteriskcentered}
\newcommand\labelitemiv{\textperiodcentered}
\newenvironment{description}
               {\list{}{\labelwidth\z@ \itemindent-\leftmargin
                        \let\makelabel\descriptionlabel}}
               {\endlist}
\newcommand*\descriptionlabel[1]{\hspace\labelsep
                                \normalfont\bfseries #1}
\newenvironment{verse}
               {\let\\\@centercr
                \list{}{\itemsep      \z@
                        \itemindent   -1.5em%
                        \listparindent\itemindent
                        \rightmargin  \leftmargin
                        \advance\leftmargin 1.5em}%
                \item\relax}
               {\endlist}
\newenvironment{quotation}
               {\list{}{\listparindent 1.5em%
                        \itemindent    \listparindent
                        \rightmargin   \leftmargin
                        \parsep        \z@ \@plus\p@}%
                \item\relax}
               {\endlist}
\newenvironment{quote}
               {\list{}{\rightmargin\leftmargin}%
                \item\relax}
               {\endlist}
\if@compatibility
\newenvironment{titlepage}
    {%
%    \cleardoublepage below - ettt for journal
     \ifnowbook \cleardoublepage \else \clearpage \fi
      \if@twocolumn
        \@restonecoltrue\onecolumn
      \else
        \@restonecolfalse\newpage
      \fi
      \thispagestyle{empty}%
      \setcounter{page}\z@
    }%
    {\if@restonecol\twocolumn \else \newpage \fi
    }
\else
\newenvironment{titlepage}
    {%
      \cleardoublepage
      \if@twocolumn
        \@restonecoltrue\onecolumn
      \else
        \@restonecolfalse\newpage
      \fi
      \thispagestyle{empty}%
      \setcounter{page}\@ne
    }%
    {\if@restonecol\twocolumn \else \newpage \fi
     \if@twoside\else
        \setcounter{page}\@ne
     \fi
    }
\fi
\def\appendix{\par
  \setcounter{chapter}{0}%
  \setcounter{section}{0}%
  \gdef\@chapapp{\appendixname}%
  \gdef\thechapter{\@Alph\c@chapter}}
\setlength\arraycolsep{5\p@}
\setlength\tabcolsep{6\p@}
\setlength\arrayrulewidth{.5\p@}
\setlength\doublerulesep{2\p@}
\setlength\tabbingsep{\labelsep}
\skip\@mpfootins = \skip\footins
\setlength\fboxsep{3\p@}
\setlength\fboxrule{.4\p@}
\@addtoreset {equation}{chapter}
\renewcommand\theequation
  {\ifnum \c@chapter>\z@ \thechapter.\fi \@arabic\c@equation}

\def\theoremfont{\upshape}

\def\@begintheorem#1#2{\goodbreak\trivlist \kern12pt\nobreak\hrule\nobreak\kern-6pt\nobreak
   \item[\hskip \labelsep{\bfseries #1\ #2.}]\theoremfont}

\def\@opargbegintheorem#1#2#3{\goodbreak\trivlist \kern12pt\nobreak\hrule\kern-6pt\nobreak
      \item[\hskip \labelsep{\bfseries #1\ #2\ (#3).}]\theoremfont}

\def\@endtheorem{\endgraf\kern6pt\nobreak\hrule \endtrivlist\par}

\def\prooffont{\upshape}
\let\proof\relax
\let\endproof\relax
\newenvironment{proof}{\par\trivlist
   \item[\hskip \labelsep{\itshape Proof.}]\prooffont}
   {\hspace*{0pt plus1fill}\fboxsep2.5pt\fboxrule.5pt\raise3pt\hbox{\fbox{}}\endtrivlist}


\newcounter{figure}[chapter]
\renewcommand \thefigure
     {\ifnum \c@chapter>\z@ \thechapter.\fi \@arabic\c@figure}
\def\fps@figure{tbp}
\def\ftype@figure{1}
\def\ext@figure{lof}
\def\fnum@figure{\figurename\nobreakspace\thefigure}
\newenvironment{figure}
               {\@float{figure}}
               {\end@float}
\newenvironment{figure*}
               {\@dblfloat{figure}}
               {\end@dblfloat}
\newcounter{table}[chapter]
\renewcommand \thetable
     {\ifnum \c@chapter>\z@ \thechapter.\fi \@arabic\c@table}
\def\fps@table{tbp}
\def\ftype@table{2}
\def\ext@table{lot}
\def\fnum@table{\tablename\nobreakspace\thetable}
\def\tablefont{\footnotesize}
\def\x{@{\extracolsep{\z@\@plus\p@}}}
\def\tablenotefont{\footnotesize}
\newenvironment{table}
               {\tablefont\topsep\z@%
                \setlength\arrayrulewidth{1\p@}%
                \@firsttabnotetrue%
                \@float{table}}
               {\end@float}
\newenvironment{table*}
               {\tablefont\@dblfloat{table}}
               {\end@dblfloat}

\newif\if@firsttabnote
\newcommand\tablefootnote[2]{{\par\if@firsttabnote\addvspace{6pt}\fi\global\@firsttabnotefalse%
   \tablenotefont%
   \noindent\@hangfrom{\textsuperscript{#1}\ }{#2\par}}}

\newlength\abovecaptionskip
\newlength\belowcaptionskip
\setlength\abovecaptionskip{9\p@}
\setlength\belowcaptionskip{6\p@}
\def\@figname{figure}
\def\figcaptionfont{\footnotesize}
\def\figcaptionnumfont{\figcaptionfont}
\def\tablecaptionfont{\footnotesize}
\def\tablecaptionnumfont{\tablecaptionfont}
\long\def\@makecaption#1#2{%
  \ifx\@captype\@figname
    \vskip\abovecaptionskip
    \sbox\@tempboxa{\figcaptionfont{\figcaptionnumfont#1}\hskip.5em#2}%
    \ifdim \wd\@tempboxa >\hsize
        \noindent{\figcaptionfont{\figcaptionnumfont#1}\hskip.5em#2\par}
    \else
        \global \@minipagefalse
        \hb@xt@\hsize{\box\@tempboxa\hfil}%
    \fi
  \else
    \sbox\@tempboxa{\tablecaptionfont{\tablecaptionnumfont#1}\hskip.5em#2}%
    \ifdim \wd\@tempboxa >\hsize
        \noindent{\tablecaptionfont{\tablecaptionnumfont#1}\hskip.5em#2\par}
    \else
        \global \@minipagefalse
        \hb@xt@\hsize{\box\@tempboxa\hfil}%
    \fi
    \vskip\belowcaptionskip
  \fi}
\DeclareOldFontCommand{\rm}{\normalfont\rmfamily}{\mathrm}
\DeclareOldFontCommand{\sf}{\normalfont\sffamily}{\mathsf}
\DeclareOldFontCommand{\tt}{\normalfont\ttfamily}{\mathtt}
\DeclareOldFontCommand{\bf}{\normalfont\bfseries}{\mathbf}
\DeclareOldFontCommand{\it}{\normalfont\itshape}{\mathit}
\DeclareOldFontCommand{\sl}{\normalfont\slshape}{\@nomath\sl}
\DeclareOldFontCommand{\sc}{\normalfont\scshape}{\@nomath\sc}
\DeclareRobustCommand*\cal{\@fontswitch\relax\mathcal}
\DeclareRobustCommand*\mit{\@fontswitch\relax\mathnormal}
\newcommand\@pnumwidth{1.55em}
\newcommand\@tocrmarg{2.55em}
\newcommand\@dotsep{4.5}
\setcounter{tocdepth}{1}
\newcommand\tableofcontents{%
    \if@twocolumn
      \@restonecoltrue\onecolumn
    \else
      \@restonecolfalse
    \fi
    \chapter*{\contentsname}
    \@mkboth{Contents}{Contents}%%%% ettt - revised 
    \thispagestyle{plain}% %% ettt - added
    \vspace*{-1.0em}
    \@starttoc{toc}%
    \if@restonecol\twocolumn\fi
    }
\newcommand*\l@part[2]{%
  \ifnum \c@tocdepth >-2\relax
    \addpenalty{-\@highpenalty}%
    \addvspace{2.25em \@plus\p@}%
    \setlength\@tempdima{3em}%
    \begingroup
      \parindent \z@ \rightskip \@pnumwidth
      \parfillskip -\@pnumwidth
      {\leavevmode
       \large \bfseries #1\hfil \hb@xt@\@pnumwidth{\hss #2}}\par
       \nobreak
         \global\@nobreaktrue
         \everypar{\global\@nobreakfalse\everypar{}}%
    \endgroup
  \fi}
\newdimen\chaptermax
\settowidth\chaptermax{Chapter\quad}
\def\chaptocfont{\normalsize\rightskip3pc plus1fill}%
\def\sectocfont{\normalsize\rightskip3pc plus1fill}%
%\newcommand*\l@chapter[2]{%
%  \ifnum \c@tocdepth >\m@ne
%    \addpenalty{-\@highpenalty}%
%%    \vskip 1.0em \@plus\p@
%    \vskip 15pt \@plus\p@
%    \setlength\@tempdima{1.5em}%
%    \begingroup
%      \parindent \z@ \rightskip \@pnumwidth
%      \parfillskip -\@pnumwidth
%      \leavevmode \bfseries
%      \advance\leftskip\@tempdima
%     \advance\leftskip\chaptermax
%      \hskip -\leftskip
%      #1\nobreak\hfil \nobreak\hb@xt@\@pnumwidth{\hss#2}\par
%      \penalty\@highpenalty
%    \endgroup
%    \par\addvspace{7.5pt}
% \fi}
\newcommand*\l@chapter[2]{%
  \ifnum \c@tocdepth >\m@ne
    \addpenalty{-\@highpenalty}%
%    \vskip 1.0em \@plus\p@
    \vskip 15pt \@plus\p@
    \setlength\@tempdima{1.5em}%
    \begingroup
      \parindent \z@
%      \rightskip \@pnumwidth \parfillskip -\@pnumwidth
      \leavevmode \bfseries
      \advance\leftskip\@tempdima
      \advance\leftskip\chaptermax
      \hskip -\leftskip
      {\chaptocfont#1\par\vskip-\baselineskip}\hbox to \textwidth{\hfill#2}%\nobreak\hfil \nobreak\hb@xt@\@pnumwidth{\hss#2}\par
      \penalty\@highpenalty
    \endgroup
    \par\addvspace{7.5pt}
 \fi}
\newcommand*\l@unnumchapter[2]{%
  \ifnum \c@tocdepth >\m@ne
    \addpenalty{-\@highpenalty}%
    \vskip 7.5\p@ \@plus\p@
    \setlength\@tempdima{0pt}%1.5em}%
    \begingroup
      \parindent \z@ \rightskip \@pnumwidth
      \parfillskip -\@pnumwidth
      \leavevmode\bfseries
      \advance\leftskip\@tempdima
      \hskip -\leftskip
      #1\nobreak\hfil \nobreak\hb@xt@\@pnumwidth{\hss #2}\par
      \penalty\@highpenalty
    \endgroup
  \fi}
\def\@undottedtocline#1#2#3#4#5{%
  \ifnum #1>\c@tocdepth \else
    \vskip \z@ \@plus.2\p@
    {\leftskip #2\relax \rightskip \@tocrmarg \parfillskip -\rightskip
     \parindent #2\relax\@afterindenttrue
     \interlinepenalty\@M
     \leavevmode
     \@tempdima #3\relax
     \advance\leftskip \@tempdima \null\nobreak\hskip -\leftskip
     {#4}\nobreak
     \leaders\hbox{%$\m@th
%        \mkern \@dotsep mu\hbox{.}\mkern \@dotsep
%        mu$
}\hfill
     \nobreak
     \hb@xt@\@pnumwidth{\hfil\normalfont \normalcolor #5}%
     \par}%
  \fi}
\newcommand*\l@section{\@undottedtocline{1}{\z@}{2.3em}}
%%
\renewcommand*\l@section[2]{%
  \ifnum \c@tocdepth >\m@ne
    \addpenalty{-\@highpenalty}%
%    \vskip 1.0em \@plus\p@
%    \vskip 15pt \@plus\p@
    \setlength\@tempdima{2.3em}%
    \begingroup
      \parindent \z@
%      \rightskip \@pnumwidth \parfillskip -\@pnumwidth
      \leavevmode% \bfseries
      \advance\leftskip\@tempdima
%     \advance\leftskip\chaptermax
      \hskip -\leftskip
      {\sectocfont#1\par\vskip-\baselineskip}\hbox to \textwidth{\hfill#2}%\nobreak\hfil \nobreak\hb@xt@\@pnumwidth{\hss#2}\par
      \penalty\@highpenalty
    \endgroup
    \par%\addvspace{7.5pt}
 \fi}
%%
\newcommand*\l@subsection{\@undottedtocline{2}{3.8em}{3.2em}}
\newcommand*\l@subsubsection{\@undottedtocline{3}{7.0em}{4.1em}}
\newcommand*\l@paragraph{\@undottedtocline{4}{10em}{5em}}
\newcommand*\l@subparagraph{\@undottedtocline{5}{12em}{6em}}
\newcommand\listoffigures{%
    \if@twocolumn
      \@restonecoltrue\onecolumn
    \else
      \@restonecolfalse
    \fi
    \chapter*{\listfigurename}%
      \@mkboth{\MakeUppercase\listfigurename}%
              {\MakeUppercase\listfigurename}%
    \@starttoc{lof}%
    \if@restonecol\twocolumn\fi
    }
\newcommand*\l@figure{\@undottedtocline{1}{1.5em}{2.3em}}
\newcommand\listoftables{%
    \if@twocolumn
      \@restonecoltrue\onecolumn
    \else
      \@restonecolfalse
    \fi
    \chapter*{\listtablename}%
      \@mkboth{%
          \MakeUppercase\listtablename}%
         {\MakeUppercase\listtablename}%
    \@starttoc{lot}%
    \if@restonecol\twocolumn\fi
    }
\let\l@table\l@figure
\newdimen\bibindent
\setlength\bibindent{1.5em}
\def\bibliofont{\small}
\newenvironment{thebibliography}[1]
     {\chapter*{\bibname}%
      \@mkboth{\bibname}{\bibname}%
      \addcontentsline{toc}{unnumchapter}{References}%
      \bibliofont%
      \list{\@biblabel{\@arabic\c@enumiv}}%
           {\settowidth\labelwidth{\@biblabel{#1}}%
            \leftmargin\labelwidth
            \advance\leftmargin\labelsep
            \@openbib@code
            \usecounter{enumiv}%
            \let\p@enumiv\@empty
            \renewcommand\theenumiv{\@arabic\c@enumiv}}%
      \sloppy
% ettt -set clubpenalty from 4000 to 10000 to prevent references from breaking
      \clubpenalty10000
      \@clubpenalty \clubpenalty
% ettt -set widowpenalty from 4000 to 10000 to prevent references from breaking
     \widowpenalty10000%
      \sfcode`\.\@m}
     {\def\@noitemerr
       {\@latex@warning{Empty `thebibliography' environment}}%
      \endlist}
\newcommand\newblock{\hskip .11em\@plus.33em\@minus.07em}
\let\@openbib@code\@empty
\newenvironment{theindex}
               {\if@twocolumn
                  \@restonecolfalse
                \else
                  \@restonecoltrue
                \fi
                \columnseprule \z@
                \columnsep 35\p@
                \twocolumn[\@makeschapterhead{\indexname}]%
                \@mkboth{\MakeUppercase\indexname}%
                        {\MakeUppercase\indexname}%
                \thispagestyle{plain}\parindent\z@
                \parskip\z@ \@plus .3\p@\relax
                \let\item\@idxitem}
               {\if@restonecol\onecolumn\else\clearpage\fi}
\newcommand\@idxitem{\par\hangindent 40\p@}
\newcommand\subitem{\@idxitem \hspace*{20\p@}}
\newcommand\subsubitem{\@idxitem \hspace*{30\p@}}
\newcommand\indexspace{\par \vskip 10\p@ \@plus5\p@ \@minus3\p@\relax}
\renewcommand\footnoterule{%
  \kern-3\p@
  \hrule\@width6pc
  \kern2.6\p@}
\@addtoreset{footnote}{chapter}
\newcommand\@makefntext[1]{%
    \parindent 0pt%
    \noindent
    \@hangfrom{\@makefnmark\ \hskip-2pt}{#1}}
\newcommand\contentsname{Contents}
\newcommand\chaptername{Chapter}
\newcommand\abstractname{Abstract}
\newcommand\listfigurename{List of Figures}
\newcommand\listtablename{List of Tables}
\newcommand\acknowledgementsname{Acknowledgements} %% ettt added
\newcommand\notationsandacronymssname{Notations and Acronyms} %% ettt added
\newcommand\bibname{References}
\newcommand\indexname{Index}
\newcommand\figurename{Fig.}
\newcommand\tablename{Table}
\newcommand\partname{Part}
\newcommand\appendixname{Appendix}
\def\today{\ifcase\month\or
  January\or February\or March\or April\or May\or June\or
  July\or August\or September\or October\or November\or December\fi
  \space\number\day, \number\year}
\setlength\columnsep{10\p@}
\setlength\columnseprule{0\p@}

%%%%%% Frontmatter %%%%%%%%%%%%%%%%%%%%%

\def\maketitle{%
  \begingroup
  \frontmatter
  \pagestyle{empty}%
  \parindent \z@
  \ifnowbook 
     \makefulltitlepage
     \maketitlepage
     \makeisbnpage
      \makeeditorpage
      \makescopepage
      \makeintropage
     \tableofcontents
  \else
     \makeintropage
  \fi
  \clearpage
  \mainmatter
  \setcounter{page}{\@firstpage}%
  \endgroup
}

\def\printedmaketitle{%
  \begingroup
  \clearpage
  \pagestyle{empty}%
  \parindent \z@
%  \ifnowbook
%      \makeeditorpage
%      \makescopepage
%      \makeissnpage
%      \makeintropage
%  \fi
  \clearpage
%  \pagenumbering{arabic}%
  \endgroup
}


\def\makefulltitlepage{%
  \vbox to \textheight{\vskip-17\p@\vss\raggedleft%
    \noindent\vrule\@height4\p@\@width265\p@\par\vskip-5\p@%
    \noindent{\fontsize{24}{36}\selectfont\sffamily\bfseries\leftskip50pt plus1fill\rightskip\z@ \@title \par}% %% ettt -\sffamily added
    \vskip9\p@%
    \noindent\vrule\@height4\p@\@width265\p@\par%
  \vss}%
  \newpage\ \newpage
}

\def\maketitlepage{%
  \vbox to\textheight{%
    \noindent\flushright{\fontsize{30}{38}\selectfont\sffamily\bfseries\leftskip0pt plus1fil\rightskip\z@ \@title \par}
    \vskip31\p@
    \noindent\vrule\@height2\p@\@width\textwidth\vskip50\p@%
    \noindent{\sffamily\normalsize\raggedleft\@authors\par}
    \vfill
    \flushright{\includegraphics{now_logo}}%
    \flushright{\sffamily \fontsize{14}{16}\selectfont Boston -- Delft}%
    \flushright{\includegraphics{essence_logo}}
}
}

\def\makeintropage{%
\noindent\hbox to \textwidth{%
    \vbox{\hsize=.5\textwidth \footnotesize
\@journaltitleprefix\\\@journaltitle\\Vol.~\@volume, No~\@issue\ (\@pubyear) 
\@firstpage--\@lastpage\\
\copyright\ \@pubyear\ \@copyrightowner\\
DOI: \@DOI}\hss
\vbox{\hsize=.4\textwidth 
    \raggedleft
    \includegraphics[width=53bp]{now_logo}\\
    \includegraphics[width=81bp]{essence_logo}%
    \par
}}\par
\vspace{24pt}
\begin{center}
{\fontsize{16}{20}\selectfont\sffamily\bfseries\@title}\par
\end{center}
\vspace{20pt}
\@theauthors\par
\vspace{20pt}
\@theinstitutes\par
\vspace{12pt}
%%%%\box\abstractbox
\@abstract
\newpage
}

\def\@theauthors{}
\def\@theinstitutes{}

\def\isbn#1{\def\@isbn{#1}}


\def\journal#1{\relax \input #1.jtl }

\def\pubyear#1{\def\@pubyear{#1}}
\def\DOI#1{\def\@DOI{#1}}
\edef\@pubyear{\the\year}

\def\issue#1{\def\@issue{#1}}
\def\volume#1{\def\@volume{#1}}
\def\firstpage#1{\def\@firstpage{#1}}

\def\@lastpage{\pageref{lastpage}}
\AtEndDocument{\label{lastpage}\clearpage \printedmaketitle }

\def\copyrightowner#1{\def\@copyrightowner{#1}}
\def\@copyrightowner{now Publishers Inc.}

\def\journaltitle#1{\def\@journaltitle{#1}}
\def\journaltitleprefix#1{\def\@journaltitleprefix{#1}}
\def\journallibraryinfo#1{\def\@libraryinfo{#1}}
\def\journalissn#1{\def\@journalissn{#1}}

\newcount\editorcount
\newcount\topiccount

\def\journaleditorinchief #1{\def\@journaleditorinchief{#1}}
\def\journaleditoraddress #1{\def\@journaleditoraddress{#1}}
\def\journaleditorcountry #1{\def\@journaleditorcountry{#1}}
\def\journaleditoremail   #1{\def\@journaleditoremail{#1}}
\def\journalissn          #1{\def\@journalissn{#1}}

\def\@editors {}

\def\journaleditor#1{%
  \advance\editorcount1 
  \ifx\empty\@editors
    \def\@editors{#1}%
  \else
    \expandafter\def\expandafter\@editors\expandafter{\@editors\\#1}%
  \fi
}

\def\@topics{}
\def\journaltopic#1{%
  \advance\topiccount1 
  \ifx\empty\@topics
    \def\@topics{\atopic{#1}}%
  \else
    \expandafter\def\expandafter\@topics\expandafter{\@topics\atopic{#1}}%
  \fi
}

\def\atopic#1{\parindent0pt\hangafter1 \hangindent1em \noindent \hbox to 1em{$\bullet$\hss}#1\par\vspace{3pt}}

\RequirePackage{multicol}

\def\makeeditorpage{%
    \begin{center}
        \sffamily {\bfseries \LARGE
       \@journaltitleprefix\\ \@journaltitle}
       \par
       \vspace{6pt}
       {\LARGE Volume \@volume\ Issue \@issue, \@pubyear}\\[6pt]
       {\LARGE\bfseries Editorial Board}
     \par
    \end{center}
       \vspace{12pt}
    {\bfseries Editor-in-Chief: \@journaleditorinchief}\\
     {\itshape\@journaleditoraddress\@journaleditorcountry\\
     \@journaleditoremail}\\
     ~\\
     %
     {\bfseries Editors}\par
     \kern-10pt
     {\fontsize{10}{13}\selectfont\begin{multicols}{2}
     \@editors
     \end{multicols}}%
     \newpage
}

\def\makescopepage{{%
    \begin{center}
        \sffamily {\bfseries\LARGE  Editorial Scope }
    \end{center}
     \vspace{18pt}
     {\bfseries  \@journaltitleprefix\ \@journaltitle}  will publish
survey and tutorial articles in the following topics:\par
     \fontsize{10}{13}\selectfont
     \begin{multicols}{2}
     \raggedright
     \@topics
     \end{multicols}%
     \vspace{12pt}
     {\bfseries\large Information for Librarians}\\
     \@libraryinfo\par
     \newpage
}}

\long\def\makeissnpage{%
    \begingroup
    \fontsize{11}{12}\selectfont
     {\bfseries \sffamily \fontsize{14}{16}\selectfont\@journaltitleprefix\\ 
      \@journaltitle\endgraf}
    \vspace{12pt}
    {\itshape Published, sold and distributed by:}\\
    now Publishers Inc.\\
    PO Box 1024\\
    Hanover, MA\ 02339\\
    USA\\
    Tel.\ +1-781-985-4510\\
    www.nowpublishers.com\\
    sales@nowpublishers.com\\[24pt]
    {\itshape Outside North America:}\\
    now Publishers Inc.\\
    PO Box 179\\
    2600 AD Delft\\
    The Netherlands\\
    Tel.\ +31-6-51115274 \par
    \vspace{6pt}
    {\itshape\small Printed on acid-free paper}\par
    \vspace{6pt}
    \@journalissn\\
    \copyright\ \@pubyear\ \@copyrightowner \par
    \vspace{6pt}
    All rights reserved. No part of this publication may be
reproduced, stored in a retrieval system, or transmitted in any form
or by any means, mechanical, photocopying, recording or otherwise,
without prior written permission of the publishers.\par
    \vspace{6pt}
  now Publishers Inc. has an exclusive license to publish this
material worldwide. Permission to use this content must be obtained from
the copyright license holder. Please apply to now Publishers, PO Box 179, 2600
AD Delft, The Netherlands, www.nowpublishers.com; e-mail:
sales@nowpublishers.com \par
\newpage
\endgroup
}

\long\def\makeisbnpage{%
    \begingroup
    \fontsize{9}{10.5}\selectfont
    \parskip6pt

     {\bfseries \sffamily \fontsize{14}{16}\selectfont\@journaltitleprefix\\ 
      \@journaltitle\endgraf}
    \vspace{12pt}
    {\itshape Published, sold and distributed by:}\\
    now Publishers Inc.\\
    PO Box 1024\\
    Hanover, MA\ 02339\\
    USA\\
    Tel.\ +1-781-985-4510\\
    www.nowpublishers.com\\
    sales@nowpublishers.com

    \vspace{12pt}
    {\itshape Outside North America:}\\
    now Publishers Inc.\\
    PO Box 179\\
    2600 AD Delft\\
    The Netherlands\\
    Tel.\ +31-6-51115274

    \vspace{12pt}
    A Cataloging-in-Publication record is available from the Library
    of Congress

    The preferred citation for this publication is
    \@copyrightowner, \@title, Foundation and Trends${}^{\textregistered}$ in 
     \@journaltitle, vol~\@volume, no~\@issue, 
       pp~\@firstpage--\@lastpage, \@pubyear\par

    {\itshape Printed on acid-free paper}\par

    ISBN: \@isbn\\ \copyright\ \@pubyear\ \@copyrightowner \par

   \vfill
   \parskip 3pt
    \fontsize{8}{9}\selectfont
    All rights reserved. No part of this publication may be
reproduced, stored in a retrieval system, or transmitted in any form
or by any means, mechanical, photocopying, recording or otherwise,
without prior written permission of the publishers.\par

\indent Photocopying. In the USA: This journal is registered at the Copyright
Clearance Center, Inc., 222 Rosewood Drive, Danvers, MA 01923. 
Authorization to photocopy items for
internal or personal use, or the internal or personal use of specific
clients, is granted by now Publishers Inc for users registered with 
the Copyright Clearance Center (CCC).
The `services' for users can be found on the internet at:
www.copyright.com\par

\indent For those organizations that have been granted a photocopy license, a
separate system of payment has been arranged. Authorization does not
extend to other kinds of copying, such as that for general
distribution, for advertising or promotional purposes, for creating
new collective works, or for resale.  In the rest of the world:
Permission to photocopy must be obtained from the copyright
owner. Please apply to now Publishers Inc., PO Box 1024, Hanover, MA
02339, USA; Tel. +1 781 871 0245; www.nowpublishers.com;
sales@nowpublishers.com\par

\indent  now Publishers Inc. has an exclusive license to publish this
material worldwide. Permission to use this content must be obtained from
the copyright license holder. Please apply to now Publishers, PO Box 179, 2600
AD Delft, The Netherlands, www.nowpublishers.com; e-mail:
sales@nowpublishers.com \par
\newpage
\endgroup
}

%
\newcount\authorcount
\def\articletitle#1{\gdef\@title{#1}}
\def\authorname#1#2{\expandafter\gdef\csname author\romannumeral#1\endcsname{#2}\global\authorcount#1}
\def\affiliation#1#2{\expandafter\gdef\csname affil\romannumeral#1\endcsname{#2}}

\def\authoradd #1#2line#3{\expandafter\gdef\csname @auaddline\romannumeral#1\endcsname{#3}}
\def\authorcity #1#2y#3{\expandafter\gdef\csname @aucity\romannumeral#1\endcsname{#3}}
\def\authorzip #1#2p#3{\expandafter\gdef\csname @auzip\romannumeral#1\endcsname{#3}}
\def\authorcountry #1#2try#3{\expandafter\gdef\csname @aucountry\romannumeral#1\endcsname{#3}}
\def\authoremail #1#2il#3{\expandafter\gdef\csname @auemail\romannumeral#1\endcsname{#3}}
\def\authortelephone #1#2ne#3{\expandafter\gdef\csname @autelephone\romannumeral#1\endcsname{#3}}

\def\author#1#2#3{%
    \ifx#2c
        \ifx#3i
            \edef\reserved@a{\noexpand\authorcity#1}
        \else
            \edef\reserved@a{\noexpand\authorcountry#1}
        \fi
    \else
        \ifx#2a
            \edef\reserved@a{\noexpand\authoradd#1}
        \else
            \ifx#2z
                \edef\reserved@a{\noexpand\authorzip#1}
            \else
                \ifx#2e
                    \edef\reserved@a{\noexpand\authoremail#1}
                \else
                    \ifx#2t
                        \edef\reserved@a{\noexpand\authortelephone#1}
                    \else
                        \edef\reserved@a{}
                    \fi
                \fi
            \fi
        \fi
    \fi\reserved@a}


\newcount\tempcount

\def\ifnonempty#1{}

\def\getvalue#1{\expandafter \csname #1\endcsname}

\def\typesetifdefined#1#2#3{%
   \@ifundefined
     {#1\romannumeral\tempcount}
     {}
     {#2\getvalue{#1\romannumeral\tempcount}#3}%
}

\def\@authors{{%
\tempcount\@ne
\@whilenum\authorcount>0
\do{%
  \typesetifdefined
    {author}
    {\noindent\bgroup\fontsize{16}{18}\selectfont\bfseries}
    {\endgraf\addvspace{18pt}\egroup}
  \noindent 
  \begingroup
    \fontsize{14}{16}\selectfont
    \itshape
    \typesetifdefined{affil}{}{\endgraf}
    \typesetifdefined{@auaddline}{}{\endgraf}
    \typesetifdefined{@aucity}{}{}
    \typesetifdefined{@auzip}{}{,}
    \typesetifdefined{@aucountry}{}{\endgraf}
    \vskip4\p@ 
    \typesetifdefined{@auemail}{}{\endgraf}
    \typesetifdefined{@autelephone}{}{\endgraf}
  \endgroup
  \vskip30\p@
  \advance\authorcount\m@ne
  \advance\tempcount\@ne}
}}

\def\@theauthors{{%
  \centering\fontsize{14}{16}%
    \selectfont\bfseries
  {\tempcount\@ne
  \@whilenum\authorcount>1 \do{%
    \ifnum\tempcount>1,~\fi
    \anauthor{\the\tempcount}%
    \advance\authorcount\m@ne
    \advance\tempcount\@ne
  }}%
  \ifnum\authorcount>1
    ~and~
  \fi
  \anauthor{\the\authorcount}%
  \par
  }%
}


\def\anauthor#1{%
   \expandafter\csname author\romannumeral#1\endcsname\textsuperscript{#1}%
}


\def\@theinstitutes{{%
  \fontsize{10}{12}\selectfont\itshape
  {\tempcount\@ne
   \@whilenum\authorcount>0\do{%
    \anaff{\the\tempcount}%
    \advance\authorcount\m@ne
    \advance\tempcount\@ne
  }}%
  \par
  }%
}

\def\anaff#1{{\par
  \def\\{}%
  \noindent \hangafter1\hangindent1em 
  \hbox to 1em{\textsuperscript{#1}\hss}%
\@ifundefined{@auaddline\romannumeral#1}{}{%
    \expandafter\csname @auaddline\romannumeral#1\endcsname}%
\@ifundefined{@aucity\romannumeral#1}{}{%
    \expandafter\csname @aucity\romannumeral#1\endcsname}%
\@ifundefined{@auzip\romannumeral#1}{}{%
    \expandafter\csname @auzip\romannumeral#1\endcsname, }%
\@ifundefined{@aucountry\romannumeral#1}{}{%
    \expandafter\csname @aucountry\romannumeral#1\endcsname}%
\@ifundefined{@auemail\romannumeral#1}{}{, %
    \expandafter\csname @auemail\romannumeral#1\endcsname}%
\par
}}



\DeclareFontShape{OT1}{cmr}{bx}{n}
   {%
      <5><6><7><8><9>gen*cmbx%
      <10><10.95>cmbx10%
      <->cmbx12%
      }{}
\DeclareFontShape{OT1}{cmss}{m}{sl}
    {%
      <->cmssi10%
      }{}
\DeclareFontShape{OT1}{cmss}{bx}{n}
     {%
      <->cmssbx10%
      }{}
\DeclareFontShape{OT1}{cmtt}{m}{n}
     {%
      <->cmtt10%
      }{}

%%%%%% end Frontmatter %%%%%%%%%%%%%%%%%%%%%


%%%%%% Backmatter %%%%%%%%%%%%%%%%%%%%% - ettt added

\newenvironment{acknowledgements}
     {\chapter*{\acknowledgementsname}%
      \@mkboth{\acknowledgementsname}{\acknowledgementsname}%
      \addcontentsline{toc}{unnumchapter}{Acknowledgements}%
      \sfcode`\.\@m}
     {\def\@noitemerr
       {\@latex@warning{Empty `theacknowledgements' environment}}%
      \endlist}

\newenvironment{notationsandacronyms}
     {\chapter*{\notationsandacronymssname}%
      \@mkboth{\notationsandacronymssname}{\notationsandacronymssname}%
      \addcontentsline{toc}{unnumchapter}{Notations and Acronyms}%
      \sfcode`\.\@m}
     {\def\@noitemerr
       {\@latex@warning{Empty `thenotationsandacronyms' environment}}%
      \endlist}

%%%%%% End Backmatter %%%%%%%%%%%%%%%%%%%%% 

\def\cleardoublepage{\clearpage\if@twoside \ifodd\c@page\else
    \hbox{}\thispagestyle{empty}\newpage\if@twocolumn\hbox{}\newpage\fi\fi\fi}

\ifinpdfmode
  \ifnowpdf
    \RequirePackage[pdftex]{hyperref}
  \fi
  \RequirePackage[pdftex]{graphicx}
\else
  \ifnowpdf
    \RequirePackage[dvips]{hyperref}
  \fi
  \RequirePackage[dvips]{graphicx}
\fi


\pagestyle{headings}
\firstpage{1}
\sloppy
\flushbottom
\onecolumn
\hoffset5pt
\voffset31.5pt
\InputIfFileExists{now-hyphenation}{}{}

\RequirePackage[vcentering,hcentering,dvips]{geometry}
\geometry{papersize={216mm,279mm},total={118mm,180mm}}

\frenchspacing


\endinput
%%
%% End of file `now-journal.cls'.
