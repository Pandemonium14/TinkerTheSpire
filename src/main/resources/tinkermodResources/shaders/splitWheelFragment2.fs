// HSV to RBG from https://www.rapidtables.com/convert/color/hsv-to-rgb.html
// Rotation matrix from https://en.wikipedia.org/wiki/Rotation_matrix

const float SQRT = 1.73205;

varying vec2 v_texCoord;

uniform float lRed;
uniform float lGreen;
uniform float lBlue;
uniform float rRed;
uniform float rGreen;
uniform float rBlue;
uniform float anchorAR;
uniform float anchorAG;
uniform float anchorAB;
uniform float anchorBR;
uniform float anchorBG;
uniform float anchorBB;


uniform float u_lLumFactor;
uniform float u_rLumFactor;

uniform sampler2D u_texture;
uniform vec2 u_screenSize;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord);

    vec3 T = vec3(color.r,color.g,color.b);
    vec3 aA = vec3(anchorAR,anchorAG,anchorAB);
    vec3 aB = vec3(anchorBR,anchorBG,anchorBB);

    float lT = length(T);
    float lA = length(aA);
    float lB = length(aB);

    float distA = (abs(aA.r/lA - T.r/lT) + abs(aA.g/lA - T.g/lT) + abs(aA.b/lA - T.b/lT))/3.;
    float distB = (abs(aB.r/lB - T.r/lT) + abs(aB.g/lB - T.g/lT) + abs(aB.b/lB - T.b/lT))/3.;

    float vT = distA/(distB+distA);

    float newR = lRed + (rRed - lRed)*vT;
    float newG = lGreen + (rGreen - lGreen)*vT;
    float newB = lBlue + (rBlue - lBlue)*vT;

    vec3 newColor = vec3(newR,newG,newB)*lT;

    gl_FragColor = vec4(newColor,color.a);
}
