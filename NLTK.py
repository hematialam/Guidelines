from nltk.corpus.reader.plaintext import PlaintextCorpusReader
from nltk.tag import pos_tag
corpusdir = 'A1C/'
newcorpus = PlaintextCorpusReader(corpusdir,'.*')
dir(newcorpus)
['CorpusView', '__class__', '__delattr__', '__dict__', '__doc__', '__format__', '__getattribute__', '__hash__', '__init__', '__module__', '__new__', '__reduce__', '__reduce_ex__', '__repr__', '__setattr__', '__sizeof__', '__str__', '__subclasshook__', '__weakref__', '_encoding', '_fileids', '_get_root', '_para_block_reader', '_read_para_block', '_read_sent_block', '_read_word_block', '_root', '_sent_tokenizer', '_tag_mapping_function', '_word_tokenizer', 'abspath', 'abspaths', 'encoding', 'fileids', 'open', 'paras', 'raw', 'readme', 'root', 'sents', 'words']
# POS tagging all the words in all text files at the same time.
newcorpus.words()
tagged = pos_tag(newcorpus.words())
tagged
