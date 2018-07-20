
/*
 * Created with IntelliJ IDEA.
 * User: aaron
 * Project: RSNameFinder
 * Date: 3/15/14
 * Time: 11:42 PM
 */

/*
 * This program must be run in conjunction with the node.js based database provided in the archive as well as the
 * in-game script to listen for name change packets from the friend server. Instructions for running each of these are
 * included in their respective folders. If you need help setting up node on the droplets, getting the script running
 * in-game, additional information, etc., contact me on Skype: deejaypeaches
 */

package com.RSNameFinder;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class Registration implements Serializable {

    private final String[] NOUNS = {"ad", "it", "if", "go", "way", "art", "map", "two", "law", "mom", "lab", "mud", "pie", "son", "tea", "dad", "ear", "hat", "sir", "air", "day", "job", "end", "fat", "key", "top", "web", "fun", "oil", "age", "bad", "tax", "man", "act", "car", "dog", "sun", "war", "bus", "eye", "box", "bit", "pot", "egg", "ice", "gas", "sky", "fan", "red", "log", "net", "sea", "dot", "fee", "bat", "kid", "sex", "cap", "cup", "lie", "tip", "bag", "bed", "gap", "arm", "bet", "god", "pin", "bar", "boy", "row", "bid", "bug", "cat", "cow", "guy", "leg", "lip", "pen", "toe", "you", "can", "one", "use", "few", "she", "put", "set", "big", "cut", "try", "pay", "let", "ask", "buy", "low", "run", "due", "mix", "fly", "hit", "cry", "eat", "fix", "tap", "win", "raw", "dig", "tie", "sad", "lay", "pop", "rip", "rub", "pig", "mob", "fog", "pan", "zoo", "dam", "gun", "ash", "meat", "year", "data", "food", "bird", "love", "fact", "idea", "area", "oven", "week", "exam", "army", "goal", "news", "user", "disk", "road", "role", "soup", "math", "wood", "unit", "cell", "lake", "city", "debt", "loss", "seat", "mall", "hair", "mode", "song", "town", "wife", "gate", "girl", "hall", "meal", "poem", "desk", "king", "menu", "beer", "dirt", "gene", "lady", "poet", "tale", "time", "work", "film", "game", "life", "form", "part", "fish", "back", "heat", "hand", "book", "type", "home", "body", "size", "card", "list", "mind", "line", "care", "risk", "word", "name", "boss", "page", "term", "test", "kind", "soil", "rate", "site", "case", "boat", "cash", "plan", "side", "rule", "head", "rock", "salt", "note", "rent", "bank", "half", "fire", "step", "face", "item", "room", "view", "ball", "gift", "tool", "wind", "sign", "task", "hope", "date", "link", "post", "star", "self", "shot", "exit", "lack", "spot", "wing", "foot", "mood", "rain", "wall", "base", "pair", "text", "file", "bowl", "club", "edge", "lock", "pack", "park", "skin", "sort", "baby", "dish", "trip", "gear", "land", "sale", "tree", "wave", "belt", "copy", "drop", "path", "tour", "blue", "duty", "hour", "luck", "milk", "pipe", "team", "crew", "gold", "mark", "pain", "shop", "suit", "tone", "band", "bath", "bone", "coat", "door", "east", "hole", "hook", "nose", "rice", "bill", "cake", "code", "ease", "farm", "host", "loan", "nail", "race", "sand", "west", "wine", "blow", "chip", "dust", "golf", "iron", "mail", "mess", "pool", "shoe", "tank", "bake", "bell", "bike", "clue", "diet", "fear", "fuel", "pace", "peak", "till", "yard", "bend", "bite", "harm", "knee", "load", "neck", "ruin", "ship", "snow", "tune", "zone", "boot", "camp", "hell", "joke", "jury", "mate", "ring", "roof", "rope", "sail", "sock", "will", "many", "most", "make", "good", "look", "help", "read", "keep", "give", "long", "play", "feel", "high", "past", "show", "call", "move", "turn", "hold", "main", "cook", "cold", "deal", "fall", "talk", "tell", "cost", "glad", "rest", "safe", "stay", "rise", "walk", "pick", "lift", "stop", "gain", "rich", "save", "lead", "meet", "sell", "ride", "wait", "deep", "flow", "dump", "push", "fill", "jump", "kick", "pass", "vast", "beat", "burn", "dark", "draw", "hire", "join", "kill", "drag", "pull", "soft", "wear", "dead", "feed", "sing", "wish", "hang", "hunt", "hate", "sick", "hurt", "swim", "wash", "fold", "grab", "hide", "miss", "roll", "sink", "slip", "calm", "male", "mine", "rush", "suck", "bear", "dare", "dear", "kiss", "neat", "quit", "tear", "wake", "wrap", "port", "bomb", "corn", "tube", "wire", "ally", "fine", "hero", "flag", "jail", "hill", "coal", "mass", "drug", "arms", "poor", "moon", "noon", "zero", "navy", "seed", "root", "money", "world", "music", "power", "story", "media", "thing", "video", "movie", "basis", "paper", "child", "month", "truth", "night", "event", "phone", "scene", "death", "woman", "blood", "skill", "depth", "heart", "photo", "topic", "steak", "union", "entry", "limit", "virus", "actor", "drama", "hotel", "match", "owner", "bread", "guest", "bonus", "queen", "ratio", "tooth", "error", "river", "buyer", "chest", "honey", "piano", "salad", "apple", "cheek", "pizza", "shirt", "uncle", "youth", "water", "while", "study", "place", "field", "point", "value", "guide", "state", "radio", "price", "trade", "group", "force", "light", "level", "order", "sense", "piece", "sport", "house", "sound", "focus", "board", "range", "image", "cause", "coast", "mouse", "class", "store", "space", "stock", "model", "earth", "birth", "scale", "speed", "style", "craft", "frame", "issue", "cycle", "metal", "paint", "share", "black", "shape", "table", "north", "voice", "brush", "front", "plant", "taste", "theme", "track", "brain", "click", "staff", "sugar", "phase", "stage", "stick", "title", "novel", "carry", "fruit", "glass", "joint", "chart", "ideal", "party", "bench", "south", "stuff", "angle", "dream", "essay", "juice", "mouth", "peace", "storm", "trick", "beach", "blank", "catch", "chain", "cream", "score", "screw", "agent", "block", "court", "layer", "curve", "dress", "fight", "grade", "horse", "noise", "pause", "proof", "smoke", "towel", "wheel", "aside", "buddy", "bunch", "coach", "cross", "draft", "floor", "habit", "judge", "knife", "pound", "shame", "trust", "blame", "brick", "chair", "devil", "glove", "lunch", "nurse", "panic", "plane", "shock", "spite", "spray", "alarm", "blind", "cable", "clerk", "cloud", "plate", "skirt", "slice", "trash", "anger", "award", "candy", "clock", "crack", "fault", "grass", "motor", "nerve", "pride", "prize", "tower", "truck", "other", "great", "being", "might", "still", "start", "human", "local", "today", "major", "check", "guard", "offer", "whole", "dance", "worth", "spend", "drive", "green", "leave", "reach", "serve", "watch", "break", "visit", "cover", "white", "final", "teach", "broad", "maybe", "stand", "young", "heavy", "hello", "worry", "press", "tough", "brown", "shoot", "touch", "pitch", "total", "treat", "abuse", "print", "raise", "sleep", "equal", "claim", "drink", "guess", "minor", "solid", "weird", "count", "doubt", "round", "slide", "strip", "march", "adult", "brief", "crazy", "prior", "rough", "laugh", "nasty", "royal", "split", "train", "upper", "crash", "funny", "quote", "spare", "sweet", "swing", "twist", "usual", "brave", "grand", "quiet", "shake", "shift", "shine", "steal", "delay", "drunk", "hurry", "punch", "reply", "silly", "smile", "spell", "clash", "album", "radar", "sheep", "wheat", "tribe", "stove", "cloth", "enemy", "slave", "stone", "truce", "jewel", "crime", "trial", "fluid", "steel", "mercy", "wages", "pilot", "crops", "fence", "humor", "ocean", "mayor", "color", "tears", "steam", "chief", "civil", "grain", "people", "family", "health", "system", "thanks", "person", "method", "theory", "nature", "safety", "player", "policy", "series", "camera", "growth", "income", "energy", "nation", "moment", "office", "driver", "flight", "length", "dealer", "debate", "member", "advice", "effort", "wealth", "county", "estate", "recipe", "studio", "agency", "memory", "aspect", "cancer", "region", "device", "engine", "height", "sample", "boring", "cousin", "editor", "extent", "guitar", "leader", "singer", "tennis", "basket", "church", "coffee", "dinner", "orange", "poetry", "police", "sector", "volume", "farmer", "injury", "speech", "winner", "worker", "writer", "breath", "cookie", "drawer", "insect", "ladder", "potato", "sister", "tongue", "affair", "client", "throat", "number", "market", "course", "school", "amount", "answer", "matter", "access", "garden", "reason", "future", "demand", "action", "record", "result", "period", "chance", "figure", "source", "design", "object", "profit", "inside", "stress", "review", "screen", "medium", "bottom", "choice", "impact", "career", "credit", "square", "effect", "friend", "couple", "living", "summer", "button", "desire", "notice", "damage", "target", "animal", "author", "budget", "ground", "lesson", "minute", "bridge", "letter", "option", "plenty", "weight", "factor", "master", "muscle", "appeal", "mother", "season", "signal", "spirit", "street", "status", "ticket", "degree", "doctor", "father", "stable", "detail", "shower", "window", "corner", "finger", "garage", "manner", "winter", "battle", "bother", "horror", "phrase", "relief", "string", "border", "branch", "breast", "expert", "league", "native", "parent", "salary", "silver", "tackle", "assist", "closet", "collar", "jacket", "reward", "bottle", "candle", "flower", "lawyer", "mirror", "purple", "stroke", "switch", "bitter", "carpet", "island", "priest", "resort", "scheme", "script", "public", "common", "change", "simple", "second", "single", "travel", "excuse", "search", "remove", "return", "middle", "charge", "active", "visual", "affect", "report", "beyond", "junior", "unique", "listen", "handle", "finish", "normal", "secret", "spread", "spring", "cancel", "formal", "remote", "double", "attack", "wonder", "annual", "nobody", "repeat", "divide", "survey", "escape", "gather", "repair", "strike", "employ", "mobile", "senior", "strain", "yellow", "permit", "abroad", "prompt", "refuse", "regret", "reveal", "female", "invite", "resist", "stupid", "clergy", "circle", "rocket", "desert", "statue", "parade", "planet", "custom", "valley", "cotton", "motion", "troops", "sailor", "ballot", "forest", "prison", "bullet", "danger", "rubber", "poison", "liquid", "treaty", "crisis", "curfew", "weapon", "terror", "colony", "asylum", "victim", "beauty", "hunger", "Senate", "history", "reading", "problem", "control", "ability", "science", "library", "product", "society", "quality", "variety", "country", "physics", "thought", "freedom", "writing", "article", "fishing", "failure", "meaning", "teacher", "disease", "success", "student", "context", "finding", "message", "concept", "housing", "opinion", "payment", "reality", "passion", "setting", "college", "storage", "version", "alcohol", "highway", "mixture", "tension", "anxiety", "climate", "emotion", "manager", "respect", "charity", "outcome", "revenue", "session", "cabinet", "clothes", "drawing", "hearing", "vehicle", "airport", "arrival", "chapter", "village", "warning", "courage", "garbage", "grocery", "penalty", "wedding", "analyst", "bedroom", "diamond", "fortune", "funeral", "speaker", "surgery", "trainer", "example", "process", "economy", "company", "service", "picture", "section", "nothing", "subject", "weather", "program", "chicken", "feature", "purpose", "outside", "benefit", "account", "balance", "machine", "address", "average", "culture", "morning", "contact", "network", "attempt", "capital", "plastic", "feeling", "savings", "officer", "trouble", "maximum", "quarter", "traffic", "kitchen", "minimum", "project", "finance", "mission", "contest", "lecture", "meeting", "parking", "partner", "profile", "routine", "airline", "evening", "holiday", "husband", "mistake", "package", "patient", "stomach", "tourist", "brother", "opening", "pattern", "request", "shelter", "comment", "monitor", "weekend", "welcome", "bicycle", "concert", "counter", "leather", "pension", "channel", "comfort", "passage", "promise", "station", "witness", "general", "tonight", "current", "natural", "special", "working", "primary", "produce", "present", "support", "complex", "regular", "reserve", "classic", "private", "western", "concern", "leading", "release", "display", "extreme", "deposit", "advance", "consist", "forever", "impress", "whereas", "combine", "command", "initial", "mention", "scratch", "illegal", "respond", "convert", "recover", "resolve", "suspect", "anybody", "stretch", "factory", "blanket", "remains", "balloon", "volcano", "anarchy", "percent", "hostage", "soldier", "refugee", "citizen", "theater", "deficit", "mineral", "victory", "surplus", "missile", "barrier", "century", "mystery", "customs", "treason", "embassy", "surface", "biology", "ecology", "computer", "software", "internet", "activity", "industry", "language", "security", "analysis", "strategy", "instance", "audience", "marriage", "medicine", "location", "addition", "painting", "politics", "decision", "property", "shopping", "category", "magazine", "teaching", "customer", "resource", "patience", "solution", "attitude", "director", "response", "argument", "contract", "emphasis", "currency", "republic", "delivery", "election", "football", "guidance", "priority", "elevator", "employee", "employer", "disaster", "feedback", "homework", "judgment", "relation", "accident", "baseball", "database", "hospital", "presence", "proposal", "quantity", "reaction", "weakness", "ambition", "bathroom", "birthday", "midnight", "platform", "stranger", "sympathy", "business", "interest", "training", "practice", "research", "exercise", "building", "material", "question", "standard", "exchange", "position", "pressure", "function", "distance", "discount", "register", "campaign", "evidence", "strength", "relative", "progress", "daughter", "pleasure", "calendar", "district", "schedule", "swimming", "designer", "mountain", "occasion", "sentence", "shoulder", "vacation", "document", "mortgage", "sandwich", "surprise", "champion", "engineer", "entrance", "incident", "resident", "specific", "possible", "personal", "national", "physical", "increase", "purchase", "positive", "creative", "original", "negative", "anything", "familiar", "official", "valuable", "chemical", "conflict", "opposite", "anywhere", "internal", "constant", "external", "ordinary", "struggle", "upstairs", "estimate", "surround", "tomorrow", "religion", "passport", "ancestor", "treasure", "minister", "chairman", "criminal", "diplomat", "railroad", "dictator", "airplane", "universe", "skeleton", "ceremony", "creature", "children", "delegate", "activist", "militant", "memorial", "movement", "military", "sickness", "majority", "Congress", "minority", "grandson"};
    private final String[] ADJECTIVES = {"all", "any", "apt", "bad", "big", "dim", "dry", "far", "fat", "few", "hot", "icy", "ill", "key", "low", "mad", "new", "odd", "old", "our", "raw", "red", "sad", "shy", "tan", "wan", "wee", "wet", "wry", "able", "aged", "ajar", "arid", "back", "bare", "best", "blue", "bold", "bony", "both", "busy", "calm", "cold", "cool", "cute", "damp", "dark", "dead", "dear", "deep", "drab", "dual", "dull", "each", "easy", "even", "evil", "fair", "fake", "fast", "fine", "firm", "flat", "fond", "free", "full", "glum", "good", "gray", "grim", "half", "hard", "high", "huge", "icky", "idle", "keen", "kind", "lame", "last", "late", "lazy", "lean", "left", "limp", "live", "lone", "long", "lost", "loud", "male", "mean", "meek", "mild", "near", "neat", "next", "nice", "numb", "oily", "only", "open", "oval", "pale", "past", "pink", "poor", "posh", "puny", "pure", "rare", "rash", "real", "rich", "ripe", "rosy", "rude", "safe", "same", "sane", "sick", "slim", "slow", "smug", "soft", "some", "sore", "sour", "spry", "tall", "tame", "tart", "taut", "that", "thin", "this", "tidy", "tiny", "torn", "trim", "TRUE", "twin", "ugly", "used", "vain", "vast", "warm", "wary", "wavy", "weak", "wide", "wild", "wiry", "wise", "worn", "zany", "adept", "agile", "alert", "alive", "ample", "angry", "aware", "awful", "baggy", "basic", "black", "bland", "blank", "bleak", "blind", "blond", "bogus", "bossy", "bowed", "brave", "brief", "brisk", "brown", "bulky", "bumpy", "burly", "cheap", "chief", "clean", "clear", "close", "corny", "crazy", "crisp", "cruel", "curly", "curvy", "dense", "dirty", "dizzy", "dopey", "eager", "early", "empty", "equal", "every", "faint", "FALSE", "fancy", "fatal", "first", "fixed", "flaky", "fluid", "frail", "frank", "fresh", "front", "funny", "fussy", "fuzzy", "giant", "giddy", "glass", "grand", "grave", "great", "green", "grimy", "gross", "grown", "gummy", "hairy", "handy", "happy", "harsh", "hasty", "heavy", "hefty", "husky", "ideal", "itchy", "jaded", "joint", "jolly", "juicy", "jumbo", "jumpy", "known", "kooky", "lanky", "large", "leafy", "legal", "light", "lined", "livid", "loose", "loyal", "lucky", "lumpy", "major", "mealy", "meaty", "merry", "messy", "milky", "minor", "minty", "misty", "mixed", "moist", "moral", "muddy", "murky", "mushy", "musty", "muted", "naive", "nasty", "needy", "nifty", "nippy", "noisy", "noted", "novel", "nutty", "obese", "other", "perky", "pesky", "petty", "phony", "plain", "plump", "plush", "prime", "prize", "proud", "pushy", "quick", "quiet", "rapid", "ready", "regal", "rigid", "right", "rough", "round", "rowdy", "royal", "ruddy", "runny", "rural", "rusty", "salty", "sandy", "scaly", "scary", "shady", "sharp", "shiny", "short", "showy", "silky", "silly", "slimy", "small", "smart", "soggy", "solid", "soupy", "spicy", "staid", "stale", "stark", "steep", "stiff", "steel", "sunny", "super", "sweet", "swift", "tasty", "tense", "tepid", "testy", "these", "thick", "third", "those", "tight", "tired", "total", "tough", "tubby", "unfit", "upset", "urban", "utter", "vague", "valid", "vapid", "vital", "vivid", "weary", "weepy", "weird", "which", "white", "whole", "windy", "witty", "woozy", "wordy", "worse", "worst", "wrong", "young", "yummy", "zesty", "whose", "civil", "sorry", "inner", "aching", "acidic", "active", "actual", "adored", "afraid", "amused", "annual", "arctic", "barren", "better", "bitter", "boring", "bouncy", "bright", "broken", "bronze", "bubbly", "candid", "canine", "caring", "cheery", "chilly", "chubby", "clever", "closed", "cloudy", "clumsy", "coarse", "common", "cooked", "costly", "crafty", "creamy", "creepy", "cuddly", "dapper", "daring", "deadly", "decent", "dental", "direct", "dismal", "dreary", "doting", "double", "drafty", "droopy", "edible", "elated", "entire", "exotic", "expert", "famous", "feisty", "feline", "female", "fickle", "filthy", "flashy", "flawed", "flimsy", "fluffy", "forked", "formal", "frayed", "French", "frigid", "frilly", "frizzy", "frosty", "frozen", "frugal", "gentle", "gifted", "giving", "gloomy", "glossy", "golden", "greedy", "grubby", "grumpy", "guilty", "hearty", "hidden", "hoarse", "hollow", "homely", "honest", "humble", "hungry", "impish", "impure", "inborn", "intent", "jagged", "jaunty", "jovial", "joyful", "joyous", "junior", "kindly", "klutzy", "knobby", "knotty", "kosher", "lavish", "lawful", "likely", "linear", "liquid", "little", "lively", "lonely", "lovely", "loving", "mature", "meager", "measly", "medium", "mellow", "modern", "modest", "narrow", "nimble", "normal", "oblong", "orange", "ornate", "ornery", "paltry", "pastel", "polite", "poised", "portly", "pretty", "pricey", "proper", "purple", "putrid", "quaint", "queasy", "quirky", "ragged", "recent", "remote", "ringed", "robust", "rotten", "scarce", "scared", "second", "secret", "serene", "severe", "shabby", "shoddy", "shrill", "silent", "silver", "simple", "sinful", "single", "skinny", "sleepy", "slight", "slushy", "smoggy", "smooth", "snappy", "sneaky", "snoopy", "somber", "sparse", "speedy", "spiffy", "square", "stable", "starry", "sticky", "stingy", "stormy", "strict", "strong", "stupid", "sturdy", "subtle", "sudden", "sugary", "superb", "svelte", "sweaty", "tender", "thorny", "timely", "tinted", "tragic", "tricky", "trusty", "uneven", "unique", "united", "unripe", "unruly", "unsung", "untidy", "untrue", "unused", "upbeat", "usable", "useful", "vacant", "violet", "warped", "watery", "webbed", "weekly", "wicked", "wiggly", "wilted", "winged", "wobbly", "woeful", "wooden", "worthy", "yearly", "yellow", "zigzag", "mental", "global", "latter", "former", "unfair", "sexual", "unable", "asleep", "admired", "alarmed", "amazing", "amusing", "ancient", "angelic", "another", "antique", "anxious", "ashamed", "assured", "austere", "average", "awesome", "awkward", "babyish", "belated", "beloved", "blaring", "boiling", "bruised", "buoyant", "buttery", "buzzing", "capital", "careful", "classic", "complex", "content", "corrupt", "crooked", "crowded", "damaged", "darling", "dearest", "decimal", "defiant", "delayed", "devoted", "digital", "dimpled", "distant", "dutiful", "earnest", "elastic", "elderly", "elegant", "eminent", "enraged", "envious", "ethical", "exalted", "excited", "failing", "faraway", "far-off", "fearful", "fitting", "flowery", "focused", "foolish", "gaseous", "general", "genuine", "glaring", "gleeful", "grouchy", "growing", "harmful", "hateful", "healthy", "helpful", "hideous", "honored", "hopeful", "humming", "hurtful", "idiotic", "illegal", "immense", "jealous", "jittery", "knowing", "lasting", "leading", "likable", "limited", "limping", "lovable", "made-up", "mammoth", "married", "massive", "medical", "melodic", "miserly", "monthly", "muffled", "mundane", "natural", "naughty", "nervous", "nonstop", "notable", "noxious", "obvious", "oddball", "offbeat", "optimal", "opulent", "orderly", "organic", "overdue", "parched", "partial", "peppery", "perfect", "pitiful", "plastic", "playful", "pleased", "pointed", "popular", "potable", "present", "prickly", "primary", "private", "profuse", "prudent", "pungent", "puzzled", "radiant", "regular", "roasted", "rubbery", "rundown", "scented", "scrawny", "selfish", "serious", "several", "shadowy", "shallow", "shocked", "similar", "soulful", "Spanish", "spotted", "squeaky", "stained", "starchy", "strange", "striped", "stylish", "subdued", "tedious", "teeming", "thirsty", "thrifty", "trained", "trivial", "unaware", "unhappy", "uniform", "unkempt", "unknown", "unlined", "unlucky", "untried", "unusual", "upright", "useless", "velvety", "vibrant", "vicious", "violent", "virtual", "visible", "warlike", "wealthy", "weighty", "welcome", "willing", "winding", "worldly", "worried", "yawning", "zealous", "various", "federal", "typical", "capable", "foreign", "eastern", "logical", "curious", "absolute", "adorable", "academic", "accurate", "advanced", "agitated", "alarming", "anchored", "animated", "aromatic", "artistic", "athletic", "attached", "blissful", "blushing", "bustling", "carefree", "careless", "cautious", "charming", "cheerful", "circular", "clueless", "colorful", "colossal", "complete", "composed", "concrete", "confused", "constant", "creative", "criminal", "critical", "crushing", "cultured", "dazzling", "decisive", "definite", "deserted", "detailed", "diligent", "discrete", "disloyal", "distinct", "dramatic", "ecstatic", "educated", "electric", "enormous", "esteemed", "euphoric", "exciting", "fabulous", "faithful", "familiar", "fatherly", "favorite", "fearless", "feminine", "finished", "flawless", "flippant", "forceful", "forsaken", "fragrant", "frequent", "friendly", "fruitful", "fumbling", "generous", "gigantic", "gleaming", "glorious", "gorgeous", "graceful", "gracious", "granular", "grateful", "gripping", "grizzled", "grounded", "growling", "gruesome", "gullible", "handmade", "handsome", "harmless", "haunting", "heavenly", "helpless", "horrible", "idolized", "ignorant", "impolite", "indolent", "infamous", "inferior", "infinite", "informal", "innocent", "insecure", "internal", "intrepid", "ironclad", "jubilant", "juvenile", "lopsided", "luminous", "lustrous", "majestic", "mediocre", "menacing", "metallic", "mindless", "motherly", "nautical", "negative", "obedient", "official", "ordinary", "original", "outlying", "outgoing", "parallel", "peaceful", "perfumed", "periodic", "personal", "physical", "piercing", "pleasant", "pleasing", "polished", "positive", "possible", "powerful", "precious", "previous", "pristine", "probable", "punctual", "puzzling", "quixotic", "reckless", "reliable", "relieved", "required", "rotating", "sardonic", "scornful", "scratchy", "separate", "shameful", "shocking", "sizzling", "skeletal", "slippery", "snarling", "sociable", "specific", "spirited", "spiteful", "splendid", "spotless", "squiggly", "standard", "straight", "strident", "striking", "studious", "stunning", "suburban", "superior", "tangible", "tattered", "tempting", "terrible", "terrific", "thankful", "thorough", "trifling", "troubled", "trusting", "truthful", "ultimate", "uncommon", "unfolded", "unlawful", "unsteady", "untimely", "unwieldy", "utilized", "valuable", "variable", "vengeful", "vigilant", "vigorous", "virtuous", "wasteful", "watchful", "well-lit", "well-off", "whopping", "wrathful", "wretched", "writhing", "youthful", "pregnant", "relevant", "suitable", "numerous", "cultural", "existing", "southern", "unlikely"};
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";
    private final String BASE_EMAIL;
    private final String PASSWORD;
    private String email;
    private String session;
    private String[] names = new String[0];
    private boolean monitor;
    private int session_slot_id;


    public Registration(String base_email, String password, boolean monitor) {
        BASE_EMAIL = base_email;
        PASSWORD = password;
        session_slot_id = (int) (Math.random() * 10000000) + 1000000;
        if (monitor) {
            this.monitor = true;
            Timer timer = new Timer();
            timer.schedule(new Monitor(), 10000, 10000);
        }
    }

    static String session_id() {
        return "password";
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getEmail() {
        if (email == null) {
            registerEmail();
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public void registerName(String username, boolean snipe) {
        while (email == null) {
            registerEmail();
        }
        NameChecker nc = new NameChecker(email, PASSWORD);
        session = nc.getSession();
        while (session == null) {
            session = new SessionGenerator().generateSessionId(email, PASSWORD);
        }

        int current = -1;
        if (username == null) {
            current = 0;
        } else if (username.contains(",")) {
            current = 0;
            names = username.split(",");
        }

        boolean checking = true;
        int i = 0;
        int status = 0;
        int error = 0;
        if (snipe) {
            if (current != -1) {
                for (int it = 0; it < names.length; it++) {
                    names[it] = names[it].trim();
                    writeNameToFile(names[it]);
                }
            } else {
                writeNameToFile(username);
            }
            do {
                if (current != -1) {
                    current++;
                    if (current >= names.length) {
                        current = 0;
                    }
                    if (names.length != 0) {
                        username = names[current];
                    }
                }

                nc.setName(username);
                switch (nc.check()) {
                    case 0:
                        System.err.println(nc.getName() + " is unavailable.");
                        status = 1;
                        break;
                    case 1:
                        System.out.println(nc.getName() + " is available.");
                        session = nc.getSession();
                        int tries = 20;
                        while (status != 2 && tries-- > 0) {
                            status = setName(session, nc.getName());
                            if (status == 1) {
                                System.out.println(nc.getName() + " is unavailable.");
                            }
                        }
                        checking = false;
                        break;
                    case 2:
                        System.err.println(nc.getName() + " is invalid.");
                        break;
                }

                if (status != 3) {
                    i = 0;
                    error = 0;
                    continue;
                }
                if (monitor && error > 5) {
                    break;
                }
                if (++i >= 3) {
                    i = 0;
                    error++;
                    session = new SessionGenerator().generateSessionId(email, PASSWORD);
                }
//                try {
//                    Thread.sleep(500L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } while (checking);
            } while (checking);
        } else {
            do {
                System.out.println("Setting name...");
                if (i > 0) {
                    session = new SessionGenerator().generateSessionId(email, PASSWORD);
                }
            } while ((status = setName(session, username)) != 2 && ++i < 5);
        }

        for (String name : names) {
            writeNameToFile(name);
        }
        writeNameToFile(username);

        if (status == 1) {
            System.out.println("That username is not available. Tried setting it anyway.");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            return;
        }
        if (status == 3) {
            System.out.println("Something went wrong setting the username.");
            System.out.println("Username: " + username);
            System.out.println("Email: " + email);
            return;
        }

        int tries = 5;
        while (tries-- > 0) {
            try {
                System.out.println("Posting details to web");
                URL url = new URL("http://example.com/log?name=" + URLEncoder.encode(username, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8"));
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setConnectTimeout(10000);
                con.setReadTimeout(10000);
                con.getInputStream();
                break;
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException while posting details to web");
            } catch (SocketTimeoutException e) {
                System.out.println("SocketTimeoutException while posting details to web");
            } catch (IOException e) {
                System.out.println("IOException while posting details to web");
                e.printStackTrace();
            }
        }
    }

    // grab list of rare names that have changed their names to something else in the past few seconds
    public void getRecentNames() {
        try {
            URLConnection conn = new URL("http://example.com/recent?ssid=" + session_slot_id).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains(",")) {
                    names = line.split(",");
                }
            }
            in.close();
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException while fetching list");
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException while fetching list");
        } catch (IOException e) {
            System.out.println("IOException while fetching list");
            e.printStackTrace();
        }
    }

    private void writeNameToFile(String username) {
        try {
            File file = new File("." + File.separatorChar + "accounts" + File.separatorChar);
            if (!file.exists()) {
                file.mkdir();
            }
            file = new File("." + File.separatorChar + "accounts" + File.separatorChar + username + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(email);
            bw.newLine();
            bw.close();
            file = new File("." + File.separatorChar + "accounts" + File.separatorChar + "emails.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.append(username).append("\t").append(email);
            bw.newLine();
            bw.close();
            System.out.println("Wrote " + email + " to file.");
        } catch (IOException e) {
            System.out.println("Error writing account to file.");
            System.out.println(username + " " + email);
            e.printStackTrace();
        }
    }

    private int setName(String session, String username) {
        try {
            URL obj = new URL("https://secure.runescape.com/m=displaynames/" + session + "/name.ws");
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);
            con.setReadTimeout(5000);
            con.setConnectTimeout(5000);
            String urlParameters = "name=" + username + "&ssl=-1&confirm=Yes";
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            ArrayList<String> page = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                page.add(line);
                if (line.contains("There was an unknown error changing your name") || line.contains("The name you have chosen is not available")) {
                    System.out.println(username + " is unavailable.");
                    br.close();
                    return 1;
                }
                if (line.contains("Your name has been changed successfully")) {
                    System.out.println("Name changed successfully.");
                    br.close();
                    return 2;
                }
                if (line.contains("Purchase RuneScape membership</a> to change your name.")) {
                    System.out.println("Purchase RuneScape membership to change your name.");
                    br.close();
                    return 3;
                }
            }
            System.out.println(page.toString());
            System.out.println("UnexpectedResponse while setting name");
        } catch (NoRouteToHostException e) {
            System.out.println("NoRouteToHostException while setting name");
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException while setting name");
            return 1;
        } catch (IOException e) {
            System.out.println("IOException while setting name");
        }
        return 3;
    }

    /*
    Generates a (hopefully) unique e-mail address using Google spam prevention features

    Given example@gmail.com, this method will return something along the lines of
        "example+lqsau9238@gmail.com",
    which is a valid e-mail address alias representing the original example@gmail.com
    */
//    private String generateUniqueEmail() {
//        String[] email = BASE_EMAIL.split("@");
//        StringBuilder sb = new StringBuilder();
//        sb.append(email[0]);
//        sb.append('+');
//        for (int i = 0; i < 5; ++i) {
//            sb.append((char) (97 + (int) (Math.random() * 26)));
//        }
//        sb.append((int) (Math.random() * 10000));
//        sb.append('@');
//        sb.append(email[1]);
//        return sb.toString();
//    }

    /*
    Generates a (hopefully) unique e-mail address using Google spam prevention features

    Given example@gmail.com, this method will return something along the lines of
        "example+SmellyRedCat285@gmail.com",
    which is a valid e-mail address alias representing the original example@gmail.com
    */
    private String generateUniqueEmail() {
        String[] email = BASE_EMAIL.split("@");
        StringBuilder sb = new StringBuilder();
        sb.append(email[0]);
        sb.append('+');
        if (Math.random() > .5D) {
            sb.append(ADJECTIVES[(int) (Math.random() * (ADJECTIVES.length - 1))]);
        } else {
            sb.append(NOUNS[(int) (Math.random() * (NOUNS.length - 1))]);
        }
//        sb.append(toProperCase(ADJECTIVES[(int) (Math.random() * (ADJECTIVES.length - 1))]));
//        sb.append(toProperCase(ADJECTIVES[(int) (Math.random() * (ADJECTIVES.length - 1))]));
//        sb.append(toProperCase(NOUNS[(int) (Math.random() * (NOUNS.length - 1))]));
        sb.append((int) (Math.random() * 1000));
        sb.append('@');
        sb.append(email[1]);
        return sb.toString();
    }

    private String toProperCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public void registerEmail() {
        System.out.println("Creating new account...");
        String email = generateUniqueEmail();
        try {
            URL obj = new URL("https://secure.runescape.com/m=account-creation/create_account_funnel.ws");
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);
            con.setReadTimeout(10000);
            con.setConnectTimeout(10000);
            String urlParameters = "onlyOneEmail=1&age=24&email1=" + URLEncoder.encode(email, "UTF-8") + "&password1=" + session_id() + "&password2=" + session_id() + "&agree_pp_and_tac=1&submit=Join+Now";
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Click the link we have included in the confirmation email.")) {
                    System.out.println("Account has been created.");
                    this.email = email;
                    writeNameToFile("ACCOUNT CREATED");
                    return;
                }
            }
        } catch (NoRouteToHostException e) {
            System.out.println("NoRouteToHostException while registering e-mail");
        } catch (SocketTimeoutException e) {
            System.out.println("SocketTimeoutException while registering e-mail");
        } catch (IOException e) {
            System.out.println("IOException while registering e-mail");
            e.printStackTrace();
        }
        this.email = null;
    }

    private class Monitor extends TimerTask {
        private Monitor() {
        }

        @Override
        public void run() {
            getRecentNames();
        }
    }
}