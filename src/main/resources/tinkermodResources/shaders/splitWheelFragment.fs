// HSV to RBG from https://www.rapidtables.com/convert/color/hsv-to-rgb.html
// Rotation matrix from https://en.wikipedia.org/wiki/Rotation_matrix

const float SQRT = 1.73205;

varying vec2 v_texCoord;

uniform float u_lRed;
uniform float u_lGreen;
uniform float u_lBlue;
uniform float u_rRed;
uniform float u_rGreen;
uniform float u_rBlue;
uniform float u_splitAngle;

uniform sampler2D u_texture;
uniform vec2 u_screenSize;

void main() {
	vec4 color = texture(u_texture, v_texCoord);

	//calculate pixel hue
    float m = min(color.r, color.g);
    m = min(m, color.b);
    float M = max(color.r, color.g);
    M = max(M, color.b);
    float hue;
    if (M == color.r) {
        hue = (color.g - color.b)/(M-m);
    } else if (M == color.g) {
        hue = 2. + (color.b - color.r)/(M-m);
    } else {
        hue = 4. + (color.r - color.g)/(M-m);
    }
    hue = hue*60.;
    if (hue < 0.) {
        hue = hue + 360.;
    }

    //figure out the split
    float lowerSplit = u_splitAngle;
    float upperSplit = lowerSplit + 180.;

    float almostL = sqrt(color.r*color.r + color.g*color.g + color.b*color.b)/SQRT;


    if (hue < lowerSplit || hue >= upperSplit) {
        vec3 newColor = vec3(u_lRed,u_lGreen,u_lBlue);
        newColor = newColor*almostL;
        gl_FragColor = vec4(newColor,color.a);
    } else {
        vec3 newColor = vec3(u_rRed,u_rGreen,u_rBlue);
                newColor = newColor*almostL;
                gl_FragColor = vec4(newColor,color.a);
    }
}
